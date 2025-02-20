package com.ontherocks.cocktail.dto;

import java.math.BigDecimal;

public class MenuDto {

    private Long menuId;
    private String name;
    private BigDecimal price;
    private String description;


    public MenuDto(Long menuId, String name, BigDecimal price, String description) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.description = description;
    }


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
