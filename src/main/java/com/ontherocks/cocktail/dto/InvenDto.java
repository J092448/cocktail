package com.ontherocks.cocktail.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class InvenDto {
    private Integer invenId;
    private Integer ingredientId;
    private String name;
    private String englishName;
    private String category;
    private String unit;
    private BigDecimal quantity;
    private BigDecimal reorderLevel;
    private Timestamp lastUpdated;
    private String status;

    public InvenDto() {}

    public InvenDto(Integer invenId, Integer ingredientId, String name, String englishName, String category,
                    String unit, BigDecimal quantity, BigDecimal reorderLevel, Timestamp lastUpdated, String status) {
        this.invenId = invenId;
        this.ingredientId = ingredientId;
        this.name = name;
        this.englishName = englishName;
        this.category = category;
        this.unit = unit;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.lastUpdated = lastUpdated;
        this.status = status;
    }

    public Integer getInvenId() { return invenId; }
    public void setInvenId(Integer invenId) { this.invenId = invenId; }

    public Integer getIngredientId() { return ingredientId; }
    public void setIngredientId(Integer ingredientId) { this.ingredientId = ingredientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEnglishName() { return englishName; }
    public void setEnglishName(String englishName) { this.englishName = englishName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(BigDecimal reorderLevel) { this.reorderLevel = reorderLevel; }

    public Timestamp getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Timestamp lastUpdated) { this.lastUpdated = lastUpdated; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFormattedQuantity() {
        if ("spirits".equals(category) || "beverages".equals(category)) {
            return quantity.multiply(BigDecimal.valueOf(1000)) + "ml";
        } else if ("fruits".equals(category)) {
            return quantity + "개";
        } else {
            return quantity + " " + unit;
        }
    }
}
