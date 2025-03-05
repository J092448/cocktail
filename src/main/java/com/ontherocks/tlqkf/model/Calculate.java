package com.ontherocks.tlqkf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calculate {
    private String month;  // 📌 월 정보 추가
    private int totalSales;  // 📌 총 매출액
    private int totalCost;  // 📌 총 매출 원가

    // 📌 기존 필드 확인 (필요하면 유지)
    private int someOtherField1;
    private int someOtherField2;
    private int someOtherField3;
    private int someOtherField4;
    private int someOtherField5;

    // ✅ 필요한 생성자 추가 (3개 인자만 받는 버전)
    public Calculate(String month, int totalSales, int totalCost) {
        this.month = month;
        this.totalSales = totalSales;
        this.totalCost = totalCost;
    }
}
