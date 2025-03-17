package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.CategoryDTO;
import com.ontherocks.cocktail.dto.IngredientDTO;
import com.ontherocks.cocktail.dto.PurchaseRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRequestService purchaseRequestService;

    // 발주 신청 생성
    public void createPurchaseRequest(PurchaseRequestDTO purchaseRequest) {
        purchaseRequest.setRequestDate(LocalDate.now()); // 신청 일자 설정
        purchaseRequestService.createPurchaseRequest(purchaseRequest);
    }

    // 모든 발주 신청 목록 조회
    public List<PurchaseRequestDTO> getAllPurchaseRequests() {
        return purchaseRequestService.getAllRequests();
    }

    // 특정 발주 신청 조회
    public PurchaseRequestDTO getPurchaseRequestById(Integer requestId) {
        PurchaseRequestDTO request = purchaseRequestService.findById(requestId);
        return request;
    }

    // 발주 신청 상태 업데이트
    public void updatePurchaseRequestStatus(Integer requestId, String status) {
        purchaseRequestService.updatePurchaseRequestStatus(requestId, status);
    }
}
