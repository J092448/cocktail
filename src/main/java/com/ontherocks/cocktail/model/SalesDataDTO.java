package com.ontherocks.cocktail.model;

// Lombok 라이브러리 import: @Data 애노테이션으로 getter, setter, toString 등을 자동 생성

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

// Lombok의 @Data 애노테이션으로 기본적인 데이터 메서드(getter, setter, toString 등)를 자동 생성
@Data
// DTO (Data Transfer Object) 클래스: 데이터를 계층 간에 효율적으로 전달하기 위해 사용
public class SalesDataDTO {

    // 판매 날짜를 나타내는 필드 (예: 2025-03-17)
    private LocalDate saleDate;

    // 총 판매 금액을 나타내는 필드 (BigDecimal을 사용하여 정확한 금액 계산 지원)
    private BigDecimal totalAmount;

    // 판매 데이터를 기록한 사용자 ID (문자열로 표현됨)
    private String userId;

    // Lombok에서 기본적으로 자동 생성되지만, 필요에 따라 명시적으로 추가된 메서드들

    // 판매 날짜 정보를 반환하는 getter 메서드
    public LocalDate getSaleDate() {
        return saleDate;
    }

    // 판매 날짜 정보를 설정하는 setter 메서드
    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    // 총 판매 금액 정보를 반환하는 getter 메서드
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    // 총 판매 금액 정보를 설정하는 setter 메서드
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    // 사용자 ID 정보를 반환하는 getter 메서드
    public String getUserId() {
        return userId;
    }

    // 사용자 ID 정보를 설정하는 setter 메서드
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
