package com.ontherocks.tlqkf.model;

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
}
