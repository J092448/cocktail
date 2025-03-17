package com.ontherocks.cocktail.dto;

public class CocktailIngredientDTO {
    private Integer ingredientId;
    private Integer cocktailId;
    private CategoryDTO category;
    private ProductDTO product; // 품목 DTO 추가


    // Getter와 Setter
    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(Integer cocktailId) {
        this.cocktailId = cocktailId;
    }
    public CategoryDTO getCategory() {
        return category;
    }
    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
    public ProductDTO getProduct() {
        return product;
    }
    public void setProduct(ProductDTO product) {
        this.product = product;
    }
    // 추가된 메서드
    public String getProductName() {
        return product != null ? product.getName() : null; // product가 null이 아닐 때 이름 반환
    }
}
