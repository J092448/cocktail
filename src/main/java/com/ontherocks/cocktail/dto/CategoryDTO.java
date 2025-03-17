package com.ontherocks.cocktail.dto;

public class CategoryDTO {
    private Integer categoryId;
    private String categoryName;

    // Getter 및 Setter
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
