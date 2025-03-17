package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.PurchaseRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseRequestMapper {
    void insertPurchaseRequest(PurchaseRequestDTO purchaseRequest); // 발주 신청을 DB에 삽입
    PurchaseRequestDTO selectPurchaseRequestById(Integer requestId); // ID로 발주 신청 조회
    List<PurchaseRequestDTO> selectAllPurchaseRequests(); // 모든 발주 신청 조회
    void updatePurchaseRequestStatus(@Param("requestId") Integer requestId, @Param("status") String status); // 발주 신청 상태 업데이트

    Integer selectMaxOrderNumber();


    String getCategoryNameById(Integer categoryId);
}

