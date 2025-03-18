package com.ontherocks.cocktail.dto;

import java.time.LocalDate;
import java.util.List;


public class SupplierDTO {
    private Integer supplierId;          // 고유 공급업체 ID
    private String businessNumber;     // 공급업체의 사업자 번호
    private String companyName;        // 업체명
    private String companyEName;      // 업체 영문명
    private String representativeName; // 대표자 이름
    private String phone;              // 공급업체 전화번호
    private String comPhone;           // 추가 전화번호
    private String category;           // 거래 품목
    private String subcategory;        // 하위 거래 품목
    private LocalDate updatedDate; //수정 날짜
    private LocalDate registrationDate; // 등록 날짜 추가
    private String categoryName; // 카테고리 이름 추가 (DB와는 무관)
    private List<ProductDTO> products; // 품목 정보를 ProductDTO 리스트로 추가



    // Getter 및 Setter

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEName() {
        return companyEName;
    }

    public void setCompanyEName(String companyEName) {
        this.companyEName = companyEName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComPhone() {
        return comPhone;
    }

    public void setComPhone(String comPhone) {
        this.comPhone = comPhone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName; // 데이터베이스에 저장되지 않음
    }
    public LocalDate getRegistrationDate() {
        return registrationDate; // 등록 날짜 getter
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate; // 등록 날짜 setter
    }


    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
