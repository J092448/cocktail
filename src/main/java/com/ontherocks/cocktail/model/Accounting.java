package com.ontherocks.cocktail.model;

// 필요한 JPA 애노테이션 및 Lombok 라이브러리 import

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 이 클래스는 데이터베이스 테이블과 매핑되는 JPA 엔티티임을 나타냄
@Entity
// 데이터베이스 테이블 이름을 지정
@Table(name = "accounting")
// Lombok 애노테이션으로 getter, setter 메서드를 자동 생성
@Getter
@Setter
public class Accounting {

    // 기본 키 필드 지정
    @Id
    // ID 값 자동 생성. 여기서는 IDENTITY 전략을 사용하여 데이터베이스에서 자동 증가 값을 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountingId;

    // `year` 필드와 데이터베이스 열을 매핑. null 값을 허용하지 않음
    @Column(nullable = false)
    private int year;

    // `month` 필드와 데이터베이스 열을 매핑. null 값을 허용하지 않음
    @Column(nullable = false)
    private int month;

    // `productSales` 필드와 데이터베이스 열을 매핑. null 값을 허용하지 않음
    @Column(nullable = false)
    private double productSales;

    // 아래는 Lombok이 이미 getter와 setter를 생성하지만, 필요시 명시적으로 추가된 메서드들

    public Long getAccountingId() {
        return accountingId;
    }

    public void setAccountingId(Long accountingId) {
        this.accountingId = accountingId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getProductSales() {
        return productSales;
    }

    public void setProductSales(double productSales) {
        this.productSales = productSales;
    }
}
