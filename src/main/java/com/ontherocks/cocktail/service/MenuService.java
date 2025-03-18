package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.MenuDto;
import com.ontherocks.cocktail.enums.MenuStatus;
import com.ontherocks.cocktail.mapper.MenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);
    private final MenuMapper menuMapper;


    @Autowired
    public MenuService(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    /**
     * ✅ 모든 메뉴 조회 (페이징 지원)
     */
    public List<MenuDto> getAllMenus(int size, int offset) {
        logger.debug("🔍 [Service] getAllMenus() 호출: size={}, offset={}", size, offset);
        List<MenuDto> menuList = menuMapper.getAllMenus(size, offset);
        logger.info("📊 [INFO] 조회된 메뉴 개수: {}", menuList.size());
        return menuList;
    }

    /**
     * ✅ 총 메뉴 개수 조회
     */
    public int getTotalMenusCount() {
        int totalMenus = menuMapper.getTotalMenusCount();
        logger.debug("📊 [INFO] 총 메뉴 개수: {}", totalMenus);
        return totalMenus;
    }

    /**
     * ✅ 특정 메뉴 상세 조회
     */
    public MenuDto getMenuById(int menuId) {
        logger.debug("🔍 [Service] getMenuById() 호출: menuId={}", menuId);
        MenuDto menu = menuMapper.getMenuById(menuId);

        if (menu == null) {
            logger.warn("⚠️ [WARN] 메뉴를 찾을 수 없음: menuId={}", menuId);
        } else {
            logger.info("✅ [SUCCESS] 메뉴 조회 완료: {}", menu);
        }
        return menu;
    }

    /**
     * ✅ 메뉴 수정
     */
    @Transactional
    public void updateMenu(MenuDto MenuDto) {
        logger.debug("📌 [DEBUG] 업데이트 요청 받은 메뉴: {}", MenuDto);

        menuMapper.updateMenu(MenuDto);
        menuMapper.updateMenuStatus(MenuDto.getMenuId(), MenuDto.getStatus().name(), MenuDto.getCocktailId());

        logger.info("✅ [SUCCESS] 메뉴 업데이트 완료!");

        // ✅ 업데이트 후 최신 정보 다시 조회
        MenuDto updatedMenu = menuMapper.getMenuById(MenuDto.getMenuId());
        if (updatedMenu == null) {
            logger.error("❌ [ERROR] 업데이트 후 메뉴 정보를 찾을 수 없음: menuId={}", MenuDto.getMenuId());
        } else {
            logger.debug("✅ [DEBUG] 업데이트된 메뉴 정보: {}", updatedMenu);
        }
    }

    public void deleteMenu(int menuId, int cocktailId) {
        try {
            // ✅ menu_list에서 먼저 삭제
            menuMapper.deleteMenuFromMenuList(menuId);

            // ✅ cocktails에서 삭제 (cocktailId가 0이 아닐 경우만)
            if (cocktailId != 0) {
                menuMapper.deleteCocktail(cocktailId);
            }

            // ✅ 마지막으로 menu 삭제
            menuMapper.deleteMenu(menuId);
        } catch (Exception e) {
            throw new RuntimeException("삭제 중 오류 발생", e);
        }
    }

    /**
     * ✅ menuId를 기반으로 cocktailId 조회
     */
    public int getCocktailIdByMenuId(int menuId) {
        MenuDto menu = menuMapper.getMenuById(menuId);
        int cocktailId = (menu != null) ? menu.getCocktailId() : 0;
        logger.debug("🔍 [INFO] getCocktailIdByMenuId(): menuId={}, cocktailId={}", menuId, cocktailId);
        return cocktailId;
    }

    /**
     * ✅ 다음 cocktailId 계산
     */
    public int getNextCocktailId(int menuId) {
        return menuId;  // ✅ menuId와 동일하게 cocktailId 설정
    }

    /**
     * ✅ 메뉴 추가
     */
    @Transactional
    public void addMenu(MenuDto MenuDto) {
        logger.info("➕ [INSERT] 신규 메뉴 추가: {}", MenuDto);

        try {
            menuMapper.insertMenu(MenuDto);
            logger.info("✅ [CHECK] menuId 값 확인: {}", MenuDto.getMenuId());
            int generatedMenuId = MenuDto.getMenuId(); // 자동 증가된 menu_id 가져오기

            Integer maxCocktailId = menuMapper.getMaxCocktailId();
            int newCocktailId = (maxCocktailId != null) ? maxCocktailId + 1 : 1;

            // ✅ MyBatis에서 인식할 수 있도록 Map을 사용하여 전달
            Map<String, Object> params = new HashMap<>();
            params.put("cocktailId", newCocktailId);
            params.put("name", MenuDto.getName());
            params.put("price", MenuDto.getPrice());
            params.put("alcoholContent", (MenuDto.getAlcoholContent() != null) ? MenuDto.getAlcoholContent() : 0.0);
            params.put("recipe", (MenuDto.getRecipe() != null && !MenuDto.getRecipe().isEmpty()) ? MenuDto.getRecipe() : "레시피 없음");

            menuMapper.insertCocktail(params);

            MenuDto.setCocktailId(newCocktailId);
            menuMapper.insertMenuList(generatedMenuId, newCocktailId, MenuDto.getStatus());

            logger.info("✅ [SUCCESS] 메뉴 등록 완료: {}", MenuDto);
        } catch (Exception e) {
            logger.error("❌ [ERROR] 메뉴 추가 실패: 메뉴명={}, 원인={}", MenuDto.getName(), e.getMessage());
            throw e;
        }
    }

    public void ensureMenuListExists(int menuId, MenuStatus status) {
        Integer count = menuMapper.getMenuListCount(menuId);
        Integer cocktailId = menuMapper.getCocktailIdByMenuId(menuId);

        if (cocktailId == null || cocktailId == 0) {
            cocktailId = 1; // 기본값 설정
            logger.warn("⚠️ [WARN] cocktail_id가 NULL 또는 0 -> 기본값(1)로 설정됨: menuId={}", menuId);
        }

        if (count == null || count == 0) {
            menuMapper.insertMenuList(menuId, cocktailId, status);
        }
    }
}

