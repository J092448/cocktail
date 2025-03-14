package com.ontherocks.cocktail.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

    @Data
    public class SalesDataDTO {
        private LocalDate saleDate;
        private BigDecimal totalAmount;
        private String userId;
    }

