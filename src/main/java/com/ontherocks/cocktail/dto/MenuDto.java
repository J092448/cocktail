package com.ontherocks.cocktail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ontherocks.cocktail.enums.MenuStatus;

import java.time.LocalDateTime;

/**
 * 메뉴 정보를 저장하고 전달하는 DTO (Data Transfer Object)
 * - 데이터베이스에서 조회한 메뉴 정보를 담아 컨트롤러, 서비스, DAO 간 데이터를 주고받을 때 사용됨
 */
public class MenuDto {

    private int menuId;                 // 메뉴 ID (PK)
    private String name;                // 메뉴 이름
    private double price;               // 가격
    private String description;         // 메뉴 설명
    private String allergyIngredients;  // 알러지 정보
    private LocalDateTime createdAt;    // 생성 날짜
    private String photoPath;           // 이미지 파일 경로
    private LocalDateTime registrationDate; // 등록 날짜
    private int cocktailId;             // 칵테일 ID (연관된 칵테일)

    @JsonFormat(shape = JsonFormat.Shape.STRING) // ✅ JSON 변환 시 Enum을 문자열로 변환
    private MenuStatus status;          // 메뉴 상태 (판매중, 품절)
    private Double alcoholContent;  // 도수 (null 허용)
    private String recipe;          // 레시피

    // ✅ 기본 생성자
    public MenuDto() {}

    // ✅ 전체 필드를 포함한 생성자 (필요 시 사용)
    public MenuDto(int menuId, String name, double price, String description,
                   String allergyIngredients, LocalDateTime createdAt,
                   String photoPath, LocalDateTime registrationDate, MenuStatus status, int cocktailId) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.allergyIngredients = allergyIngredients;
        this.createdAt = createdAt;
        this.photoPath = photoPath;
        this.registrationDate = registrationDate;
        this.status = status;
        this.cocktailId = cocktailId;

    }

    // ✅ Getter & Setter 메서드
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllergyIngredients() {
        return allergyIngredients;
    }

    public void setAllergyIngredients(String allergyIngredients) {
        this.allergyIngredients = allergyIngredients;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public MenuStatus getStatus() {
        return status;
    }

    public void setStatus(MenuStatus status) {
        this.status = status;
    }

    public int getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(int cocktailId) {
        this.cocktailId = cocktailId;
    }
    // ✅ 추가해야 하는 Getter & Setter
    public Double getAlcoholContent() {
        return alcoholContent;
    }
    public void setAlcoholContent(Double alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    public String getRecipe() {
        return recipe;
    }
    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    // ✅ 데이터베이스에서 가져온 문자열 값을 Enum(MenuStatus)으로 변환하는 보조 메서드
    public void setStatusFromString(String status) {
        this.status = MenuStatus.fromString(status);
    }

    // ✅ `status`가 `null`이면 기본값("판매중") 반환
    public String getStatusValue() {
        return (status != null) ? status.getValue() : "판매중";
    }

    // ✅ toString() 추가 (디버깅용)
    @Override
    public String toString() {
        return "MenuDto{" +
                "menuId=" + menuId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", allergyIngredients='" + allergyIngredients + '\'' +
                ", createdAt=" + createdAt +
                ", photoPath='" + photoPath + '\'' +
                ", registrationDate=" + registrationDate +
                ", status=" + (status != null ? status.getValue() : "판매중") +
                ", cocktailId=" + cocktailId +
                ", alcoholContent=" + (alcoholContent != null ? alcoholContent : "기본값 0.0") +
                ", recipe='" + (recipe != null ? recipe : "레시피 없음") + '\'' +
                '}';
    }



}
