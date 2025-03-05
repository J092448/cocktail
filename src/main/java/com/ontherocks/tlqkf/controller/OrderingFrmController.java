package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.model.OrderingFrmForm;
import com.ontherocks.tlqkf.service.OrderingFrmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderingFrm")



public class OrderingFrmController {

    private final OrderingFrmService orderingFrmService;

    public OrderingFrmController(OrderingFrmService orderingFrmService) {
        this.orderingFrmService = orderingFrmService;
    }

    // ✅ 페이지 로드 시 기본 데이터 제공 (Thymeleaf에서 사용)
    @GetMapping("/form")
    public String showOrderingForm(Model model) {
        OrderingFrmForm form = new OrderingFrmForm();
        model.addAttribute("orderingFrmForm", form); // ✅ 폼 객체 추가
        model.addAttribute("categories", orderingFrmService.getCategories()); // ✅ 카테고리 목록 추가
        return "orderingFrm"; // ✅ Thymeleaf 템플릿 반환
    }

    // ✅ 카테고리에 따른 서브카테고리 반환 (AJAX 요청)
    @ResponseBody
    @GetMapping("/subcategories")
    public List<String> getSubcategories(@RequestParam String category) {
        return orderingFrmService.getSubcategories(category);
    }

    // ✅ 서브카테고리에 따른 품목 반환 (AJAX 요청)
    @ResponseBody
    @GetMapping("/items")
    public List<String> getItems(@RequestParam String subcategory) {
        return orderingFrmService.getItemNames(subcategory);
    }

    // ✅ 품목에 따른 가격 반환 (AJAX 요청)
    @ResponseBody
    @GetMapping("/unitPrice")
    public String getUnitPrice(@RequestParam String itemName) {
        return orderingFrmService.getUnitPrice(itemName);
    }
}
