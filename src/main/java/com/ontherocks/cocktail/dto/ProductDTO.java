package com.ontherocks.cocktail.dto;

public class ProductDTO {
    private Integer id;            // 품목 ID
    private String name;          // 품목 이름
    private Integer volume;       // 용량 (ml)
    private Double price;         // 가격
    private Integer categoryId;   // 카테고리 ID
    private Integer supplierId;
    private String productType; // spirit, mixer, fruit, beverage
    private Integer spiritId; // 주류 ID
    private Integer fruitId; // 과일 ID
    private Integer beverageId; // 음료 ID
    private Integer mixerId; // 믹서 ID



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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public Integer getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public Integer getSpiritId() {
        return spiritId;
    }
    public void setSpiritId(Integer spiritId) {
        this.spiritId = spiritId;
    }
    public Integer getFruitId() {
        return fruitId;
    }
    public void setFruitId(Integer fruitId) {
        this.fruitId = fruitId;
    }
    public Integer getBeverageId() {
        return beverageId;
    }
    public void setBeverageId(Integer beverageId) {
        this.beverageId = beverageId;
    }
    public Integer getMixerId() {
        return mixerId;
    }
    public void setMixerId(Integer mixerId) {
        this.mixerId = mixerId;
    }

}
