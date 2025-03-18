package com.ontherocks.cocktail.model;

// Lombok의 Data 애노테이션을 사용하여 getter, setter, toString, equals, hashCode 메서드 자동 생성

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

// @Data를 사용해 기본적인 데이터 메서드(getter, setter, toString 등)를 자동 생성하는 클래스
@Data
// DTO (Data Transfer Object) 클래스: 데이터를 다른 계층 간에 전달하기 위해 사용
public class CalendarDTO {

    // 날짜 정보를 저장하는 필드 (예: 2023-03-17)
    private LocalDate date;

    // 매출 금액 정보를 저장하는 필드 (BigDecimal로 정밀도 보장)
    private BigDecimal salesAmount;

    // Lombok에 의해 getter와 setter가 자동 생성되지만, 필요에 따라 명시적으로 추가 가능

    // 날짜 정보를 반환하는 getter 메서드
    public LocalDate getDate() {
        return date;
    }

    // 날짜 정보를 설정하는 setter 메서드
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // 매출 금액 정보를 반환하는 getter 메서드
    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    // 매출 금액 정보를 설정하는 setter 메서드
    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }
}
