package com.ontherocks.cocktail.dto;

public class InvenFilterDto {
    private String category; // fruits, mixers, spirits, beverages
    private String status; // 정상, 재고부족, 품절

    public InvenFilterDto() {}

    public InvenFilterDto(String category, String status) {
        this.category = category;
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}