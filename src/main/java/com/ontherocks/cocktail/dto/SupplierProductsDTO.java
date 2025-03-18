package com.ontherocks.cocktail.dto;

import java.util.List;

public class SupplierProductsDTO {
    private SupplierDTO supplier;     // 공급업체 정보
    private List<ProductDTO> products; // 품목 정보 리스트

    // Getter 및 Setter
    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
