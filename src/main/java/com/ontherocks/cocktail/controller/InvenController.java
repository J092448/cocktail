package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.InvenDto;
import com.ontherocks.cocktail.service.InvenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InvenController {
    private static final Logger logger = LoggerFactory.getLogger(InvenController.class);
    private final InvenService invenService;

    public InvenController(InvenService invenService) {
        this.invenService = invenService;
    }

    /**
     * ✅ 재고 목록 조회 페이지
     */
    @GetMapping("/InvenList")
    public String invenList(
            @RequestParam(value = "category", required = false, defaultValue = "1") String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {

        int currentPage = Math.max(1, page);
        int offset = (currentPage - 1) * pageSize;

        List<InvenDto> invenList = invenService.getInvenList(category, page, pageSize);
        int totalInventories = invenService.getTotalInventoriesCount(category);
        int totalPages = (totalInventories + pageSize - 1) / pageSize;

        model.addAttribute("invenList", invenList);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);

        return "inventory/InvenList";
    }

    /**
     * ✅ 재고 상세 조회 페이지
     */
    @GetMapping("/inventory/InvenDetail")
    public String invenDetail(@RequestParam(value = "ingredientId", required = false) Integer ingredientId, Model model, RedirectAttributes redirectAttributes) {
        if (ingredientId == null || ingredientId <= 0) {
            logger.warn("⚠️ [조회 실패] 잘못된 ingredientId: {}", ingredientId);
            redirectAttributes.addFlashAttribute("errorMessage", "잘못된 접근입니다.");
            return "redirect:/InvenList";
        }

        InvenDto invenDto = invenService.getInvenDetail(ingredientId);

        if (invenDto == null) {
            logger.warn("⚠️ [조회 실패] 해당 재료가 존재하지 않습니다. ID: {}", ingredientId);
            redirectAttributes.addFlashAttribute("errorMessage", "존재하지 않는 재료입니다.");
            return "redirect:/InvenList";
        }

        model.addAttribute("invenDto", invenDto);
        model.addAttribute("ingredientId", ingredientId);

        return "inventory/InvenDetail";
    }

    /**
     * ✅ 재고 수정 페이지 이동
     */
    @GetMapping("/InvenEdit")
    public String editInven(@RequestParam(value = "ingredientId", required = false) Integer ingredientId, Model model, RedirectAttributes redirectAttributes) {
        if (ingredientId == null) {
            logger.error("❌ [에러] ingredientId가 전달되지 않음!");
            redirectAttributes.addFlashAttribute("errorMessage", "잘못된 접근입니다.");
            return "redirect:/InvenList";
        }

        InvenDto invenDto = invenService.getInvenDetail(ingredientId);
        if (invenDto == null) {
            logger.warn("⚠️ [조회 실패] 존재하지 않는 ingredientId: {}", ingredientId);
            redirectAttributes.addFlashAttribute("errorMessage", "해당 재고가 존재하지 않습니다.");
            return "redirect:/InvenList";
        }

        model.addAttribute("invenDto", invenDto);
        return "inventory/InvenEdit";
    }
}
