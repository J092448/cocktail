package com.ontherocks.cocktail.dto;

import java.time.LocalDate;

public class PurchaseRequestDTO {
    private Integer requestId;      // 고유 발주 ID
    private Integer productId;       // 품목 ID
    private String productName; // 품목명
    private Integer supplierId;      // 업체 ID
    private Integer categoryId;         // 종류
    private Integer quantity;        // 수량
    private LocalDate requestDate;   // 신청 일자
    private LocalDate dueDate;       // 납기 일자
    private String status;           // 상태
    private Integer ingredientId;
    private String ingredientName;
    private SupplierDTO supplier; // SupplierDTO 추가


    // Getter와 Setter
    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }
    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }


    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getIngredientName() {
        return ingredientName;
    }
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public SupplierDTO getSupplier() {
        return supplier;
    }
    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }





}

