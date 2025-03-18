package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.MenuDto;
import com.ontherocks.cocktail.enums.MenuStatus;
import com.ontherocks.cocktail.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Controller
public class MenuCont {

    private static final Logger logger = LoggerFactory.getLogger(MenuCont.class);
    private final MenuService menuService;

    @Autowired
    public MenuCont(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/SalesMenuList")
    public String getSalesMenuList(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        List<MenuDto> menuList = menuService.getAllMenus(pageSize, offset);
        int totalMenus = menuService.getTotalMenusCount();
        int totalPages = (int) Math.ceil((double) totalMenus / pageSize);

        model.addAttribute("menuList", menuList);
        model.addAttribute("totalMenus", totalMenus);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);

        logger.debug("✅ [DEBUG] 메뉴 리스트 조회 완료. 현재 페이지: {}, 총 페이지 수: {}", page, totalPages);

        return "menu/SalesMenuList";
    }

    @GetMapping("NewMenuFrm")
    public String showNewMenuForm(Model model) {
        model.addAttribute("menu", new MenuDto()); // 🛠️ 빈 객체 추가
        return "menu/NewMenuFrm";
    }

    @GetMapping("/menu/detail/{menuId}")
    public String getMenuDetail(@PathVariable int menuId, Model model) {
        MenuDto menu = menuService.getMenuById(menuId);

        if (menu == null) {
            model.addAttribute("errorMessage", "메뉴 정보를 찾을 수 없습니다.");
            return "error";
        }

        model.addAttribute("menu", menu);
        return "menu/MenuDetail";
    }

    @GetMapping("/menu/update/{menuId}")
    public String showUpdateForm(@PathVariable("menuId") int menuId, Model model) {
        MenuDto menu = menuService.getMenuById(menuId);

        if (menu == null) {
            System.out.println("❌ 메뉴 정보를 찾을 수 없음! (menuId: " + menuId + ")");
            model.addAttribute("errorMessage", "메뉴 정보를 찾을 수 없습니다.");
            return "error";
        }

        // ✅ 디버깅: menu.status 값 확인
        System.out.println("🔹 메뉴 불러오기 성공! 메뉴 ID: " + menu.getMenuId());
        System.out.println("🔹 메뉴 상태: " + menu.getStatus()); // 상태값 확인

        model.addAttribute("menu", menu);
        return "menu/MenuUpdate";
    }

    @PostMapping("/menu/delete/{menuId}/{cocktailId}")
    public String deleteMenu(@PathVariable("menuId") int menuId,
                             @PathVariable("cocktailId") int cocktailId,
                             RedirectAttributes redirectAttributes) {
        try {
            menuService.deleteMenu(menuId, cocktailId);
            redirectAttributes.addFlashAttribute("message", "선택하신 메뉴가 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류 발생.");
        }
        return "redirect:/SalesMenuList";
    }



    @PostMapping("/menu/update")
    public String updateMenu(
            @RequestParam("menuId") int menuId,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("allergyIngredients") String allergyIngredients,
            @RequestParam(value = "status", required = false) MenuStatus status,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            RedirectAttributes redirectAttributes) {

        logger.info("📌 [DEBUG] 메뉴 업데이트 요청: menuId={}, status={}", menuId, status);

        MenuDto existingMenu = menuService.getMenuById(menuId);
        if (existingMenu == null) {
            redirectAttributes.addFlashAttribute("error", "메뉴 정보를 찾을 수 없습니다.");
            logger.warn("⚠️ [WARN] 메뉴 ID {} 찾을 수 없음.", menuId);
            return "redirect:/SalesMenuList";
        }

        // DTO 생성 및 기존 정보 유지
        MenuDto MenuDto = new MenuDto();
        MenuDto.setMenuId(menuId);
        MenuDto.setName(name);
        MenuDto.setPrice(price);
        MenuDto.setDescription(description);
        MenuDto.setAllergyIngredients(allergyIngredients);
        MenuDto.setStatus(status != null ? status : existingMenu.getStatus());

        // ✅ 기존 이미지 유지 (새 이미지가 없을 경우)
        String photoPath = existingMenu.getPhotoPath();
        if (photo != null && !photo.isEmpty()) {
            try {
                String uploadDir = "C:/cocktail_bar/images/";
                File directory = new File(uploadDir);
                if (!directory.exists()) directory.mkdirs();

                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                photoPath = "/images/" + fileName;
            } catch (Exception e) {
                logger.error("❌ [ERROR] 이미지 업로드 실패: {}", e.getMessage(), e);
            }
        }
        MenuDto.setPhotoPath(photoPath);

        // 메뉴 업데이트 수행
        menuService.updateMenu(MenuDto);
        menuService.ensureMenuListExists(menuId, MenuDto.getStatus());

        redirectAttributes.addFlashAttribute("message", "메뉴가 성공적으로 수정되었습니다.");
        logger.info("✅ [SUCCESS] 메뉴 ID {} 업데이트 완료.", menuId);

        // ✅ 업데이트 후 메뉴 리스트 및 상세 페이지 최신 정보 적용
        return "redirect:/menu/detail/" + menuId;
    }

    @PostMapping("/menu/add")
    public ResponseEntity<Map<String, Object>> addMenu(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("allergyIngredients") String allergyIngredients,
            @RequestParam("status") String status,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        try {
            String uploadDir = "C:/cocktail_bar/images/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "default.jpg";
            if (photo != null && !photo.isEmpty()) {
                fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            MenuDto MenuDto = new MenuDto();
            MenuDto.setName(name);
            MenuDto.setPrice(price);
            MenuDto.setDescription(description);
            MenuDto.setAllergyIngredients(allergyIngredients);
            MenuDto.setStatus(MenuStatus.valueOf(status));
            MenuDto.setPhotoPath("/images/" + fileName);

            // ✅ 먼저 메뉴를 추가하여 menuId를 생성
            menuService.addMenu(MenuDto);

            // ✅ 생성된 menuId를 cocktailId로 설정
            int generatedMenuId = MenuDto.getMenuId();
            MenuDto.setCocktailId(generatedMenuId);

            // ✅ menu_list에도 추가
            menuService.ensureMenuListExists(generatedMenuId, MenuDto.getStatus());

            logger.info("✅ [SUCCESS] 메뉴 등록 완료: {}", MenuDto);

            // ✅ JSON 응답 반환
            Map<String, Object> response = Map.of(
                    "message", "등록이 완료되었습니다.",
                    "menuId", generatedMenuId
            );

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            logger.error("❌ [ERROR] 메뉴 등록 실패: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "메뉴 등록 실패", "details", e.getMessage()));
        }
    }
}


