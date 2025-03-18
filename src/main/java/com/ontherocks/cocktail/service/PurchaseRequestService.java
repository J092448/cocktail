package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.ProductDTO;
import com.ontherocks.cocktail.dto.PurchaseRequestDTO;
import com.ontherocks.cocktail.mapper.ProductMapper;
import com.ontherocks.cocktail.mapper.PurchaseRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseRequestService {
    @Autowired
    private PurchaseRequestMapper purchaseRequestMapper;
    @Autowired
    private ProductMapper productMapper; // ProductMapper 추가

    // 발주 요청 생성
    public void createPurchaseRequest(PurchaseRequestDTO purchaseRequestDTO) {
        // ingredientId로 품목 조회
        ProductDTO product = productMapper.getProductById(purchaseRequestDTO.getIngredientId());
        if (product != null) {
            purchaseRequestDTO.setProductId(product.getId()); // 품목 ID 설정
            purchaseRequestDTO.setProductName(product.getName()); // 품목 이름 설정
        } else {
            purchaseRequestDTO.setProductName("품목 없음");
        }

        purchaseRequestDTO.setRequestDate(LocalDate.now()); // 신청일자 자동 설정
        purchaseRequestDTO.setDueDate(LocalDate.now().plusDays(3)); // 납기 일자 설정
        purchaseRequestDTO.setStatus("수령대기"); // 기본 상태 설정
        purchaseRequestMapper.insertPurchaseRequest(purchaseRequestDTO); // 데이터베이스에 추가
    }



    // 발주 ID로 발주 조회
    public PurchaseRequestDTO findById(Integer requestId) {
        return purchaseRequestMapper.selectPurchaseRequestById(requestId); // 데이터베이스에서 조회
    }

    // 발주 상태 업데이트
    public void updatePurchaseRequestStatus(Integer requestId, String status) {
        PurchaseRequestDTO request = findById(requestId);
        if (request != null) {
            request.setStatus(status); // 상태 업데이트
            purchaseRequestMapper.updatePurchaseRequestStatus(requestId, status); // 데이터베이스에 업데이트
        }
    }

    // 모든 발주 요청 반환
    public List<PurchaseRequestDTO> getAllRequests() {
        return purchaseRequestMapper.selectAllPurchaseRequests(); // 데이터베이스에서 모든 요청 조회
    }
}
