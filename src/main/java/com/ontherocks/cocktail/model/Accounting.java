package com.ontherocks.cocktail.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accounting")
@Getter
@Setter
public class Accounting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountingId;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private double productSales;

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
