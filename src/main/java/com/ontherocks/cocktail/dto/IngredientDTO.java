package com.ontherocks.cocktail.dto;

public class IngredientDTO {
    private Integer id;            // 재료 ID
    private String name;          // 재료 이름
    private Integer categoryId;   // 카테고리 ID
    private Integer productId;    // 제품 ID

    // Getter 및 Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
