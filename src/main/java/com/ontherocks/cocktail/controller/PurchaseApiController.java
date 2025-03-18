package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.ProductDTO;
import com.ontherocks.cocktail.dto.PurchaseRequestDTO;
import com.ontherocks.cocktail.service.ProductService;
import com.ontherocks.cocktail.service.PurchaseRequestService;
import com.ontherocks.cocktail.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseApiController {

    @Autowired
    private PurchaseRequestService purchaseRequestService;

    @Autowired
    private PurchaseService purchaseService; // 서비스 주입
    @Autowired
    private ProductService productService; // 서비스 주입

    // 발주 신청 생성
    @PostMapping("/request")
    public ResponseEntity<Void> createPurchaseRequest(@RequestBody PurchaseRequestDTO purchaseRequest) {
        purchaseService.createPurchaseRequest(purchaseRequest); // 발주 신청 생성
        return ResponseEntity.status(HttpStatus.FOUND) // 302 Found 상태 코드
                .location(URI.create("/purchase/list")) // 리다이렉트할 URL
                .build(); // 응답 생성
    }

    // 발주 상태 업데이트
    @PutMapping("/request/{id}/status")
    public ResponseEntity<Void> updateRequestStatus(@PathVariable Integer id, @RequestBody String status) {
        purchaseService.updatePurchaseRequestStatus(id, status); // 상태 업데이트
        return ResponseEntity.ok().build(); // 상태 업데이트 응답
    }

    // 발주 상태를 '수령완료'로 변경하는 메서드
    @PostMapping("/receive/{requestId}")
    public ResponseEntity<String> receiveOrder(@PathVariable("requestId") Integer requestId) {
        // 발주 상태 변경 로직
        PurchaseRequestDTO purchaseRequest = purchaseRequestService.findById(requestId);

        if (purchaseRequest == null) {
            return ResponseEntity.notFound().build(); // 발주가 존재하지 않는 경우
        }

        // 상태를 '수령완료'로 변경
        purchaseRequestService.updatePurchaseRequestStatus(requestId, "수령완료");

        return ResponseEntity.ok("수령 처리 완료"); // 성공 응답
    }

    // 모든 발주 신청 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<PurchaseRequestDTO>> getPurchaseRequests() {
        List<PurchaseRequestDTO> requests = purchaseService.getAllPurchaseRequests(); // 발주 신청 목록 조회
        return ResponseEntity.ok(requests); // 모든 발주 신청 목록을 반환
    }

    // 품목 목록을 가져오는 API
    @GetMapping("/items") // 경로를 /api/purchase/items로 설정
    public ResponseEntity<List<ProductDTO>> getItemsByCategory(@RequestParam Integer categoryId) {
        List<ProductDTO> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products); // 카테고리 ID로 품목 목록 반환
    }

    // 특정 발주 신청의 상세 정보를 조회하는 API
    @GetMapping("/request/{id}")
    public ResponseEntity<PurchaseRequestDTO> getPurchaseRequestById(@PathVariable Integer id) {
        PurchaseRequestDTO purchaseRequest = purchaseRequestService.findById(id); // 발주 신청 조회

        if (purchaseRequest == null) {
            return ResponseEntity.notFound().build(); // 발주가 존재하지 않을 경우 404 응답
        }

        return ResponseEntity.ok(purchaseRequest); // 발주 신청 상세 정보를 반환
    }

}
