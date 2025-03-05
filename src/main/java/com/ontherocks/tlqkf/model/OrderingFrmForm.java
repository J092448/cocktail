package com.ontherocks.tlqkf.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderingFrmForm {
    private String category;
    private String subcategory;
    private String itemName;
    private String company;
    private String businessNumber; // 거래 업체 선택 시 자동 입력
    private BigDecimal unitPrice;  // 품목 선택 시 자동 입력
}
