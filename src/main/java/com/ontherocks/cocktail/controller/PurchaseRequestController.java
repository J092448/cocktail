package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.CategoryDTO;
import com.ontherocks.cocktail.dto.PurchaseRequestDTO;
import com.ontherocks.cocktail.dto.SupplierDTO;
import com.ontherocks.cocktail.mapper.PurchaseRequestMapper;
import com.ontherocks.cocktail.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/purchase") // UI 경로 그룹화
public class PurchaseRequestController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private PurchaseRequestService purchaseRequestService;
    @Autowired
    private PurchaseRequestMapper purchaseRequestMapper; // MyBatis Mapper 주입
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CategoryService categoryService; // 카테고리 서비스 추가
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private ProductService productService;


    // GET 메서드: 발주 목록 페이지 로드
    @GetMapping("/list")
    public String showPurchaseList(Model model) {
        List<PurchaseRequestDTO> requests = purchaseService.getAllPurchaseRequests(); // 발주 신청 목록 조회

        // 각 요청의 품목 이름 추가
        for (PurchaseRequestDTO request : requests) {
            // ingredientId를 사용하여 품목 이름 가져오기
            String productName = productService.getProductNameByIngredientId(request.getIngredientId());
            request.setProductName(productName); // DTO에 품목 이름 설정
        }

        model.addAttribute("requests", requests); // 모델에 발주 신청 목록 추가
        return "pages/orderingList"; // 발주 목록 페이지 반환
    }


    // GET 메서드: 발주 신청 페이지 로드
    @GetMapping("/orderingFrm")
    public String showOrderForm(Model model) {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers(); // 공급업체 목록
        List<CategoryDTO> categories = categoryService.getAllCategories(); // 카테고리 목록

        model.addAttribute("suppliers", suppliers);
        model.addAttribute("categories", categories); // 카테고리 목록 추가
        model.addAttribute("orderNumber", generateOrderNumber()); // 발주 번호 생성 로직
        model.addAttribute("currentDate", LocalDate.now().toString()); // 현재 날짜
        model.addAttribute("expectedDate", LocalDate.now().plusDays(3).toString()); // 예상 수령일
        return "pages/orderingFrm"; // Thymeleaf 템플릿 이름
    }

    // POST 메서드: 발주 신청 처리
    @PostMapping("/request") // 경로를 /purchase/request로 설정
    public String createPurchaseRequest(PurchaseRequestDTO purchaseRequestDTO) {
        purchaseRequestDTO.setRequestDate(LocalDate.now()); // 신청 일자 설정
        purchaseRequestDTO.setDueDate(LocalDate.now().plusDays(3)); // 납기 일자 설정
        purchaseRequestDTO.setRequestId(generateOrderNumber()); // 발주 번호 생성
        purchaseRequestService.createPurchaseRequest(purchaseRequestDTO);
        return "redirect:/purchase/list"; // 발주 목록 페이지로 리다이렉트
    }

    // 발주 번호 생성 메서드
    private Integer generateOrderNumber() {
        Integer maxOrderNumber = purchaseRequestMapper.selectMaxOrderNumber(); // 최대 발주 번호 조회
        if (maxOrderNumber == null) {
            return 1; // 첫 번째 발주 경우
        } else {
            return maxOrderNumber + 1; // 최대 발주 번호에 1 더하기
        }
    }
    // GET 메서드: 발주 상세 페이지
    @GetMapping("/detail/{requestId}")
    public String showOrderDetail(@PathVariable Integer requestId, Model model) {
        PurchaseRequestDTO orderDetail = purchaseOrderService.getOrderDetailById(requestId);
        model.addAttribute("orderDetail", orderDetail);
        return "pages/orderingDetail"; // 타임리프 템플릿 경로
    }
}
