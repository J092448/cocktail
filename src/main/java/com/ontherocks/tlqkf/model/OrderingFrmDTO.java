package com.ontherocks.tlqkf.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderingFrmDTO {
    private Long id;              // 발주 ID
    private String category;      // 발주 물품 대분류
    private String subcategory;   // 발주 물품 소분류
    private String itemName;      // 품목명
    private String company;       // 거래업체
    private String businessNumber; // 사업자 번호
    private BigDecimal unitPrice; // 단가
    private Integer quantity;     // 발주 신청 수량
    private BigDecimal totalAmount; // 총 발주 금액
    private LocalDate orderDate;  // 발주 신청 일자
    private LocalDate expectedDate; // 예상 수령 일자
}
