package com.ontherocks.cocktail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String username;
    private String name;
    private String companyName;
    private String password;
    private String newPassword;
    private String phoneNumber;
    private String secondaryPhoneNumber;
    private String businessNumber;
    private String email;
    private boolean accountNonLocked;
    private LocalDateTime registrationDate;

//    public UserDto(Integer userId, String username, String name, String companyName, String password, String newPassword, String phoneNumber, String secondaryPhoneNumber, String businessNumber, String email, boolean accountNonLocked, LocalDateTime registrationDate) {
//        this.userId = userId;
//        this.username = username;
//        this.name = name;
//        this.companyName = companyName;
//        this.password = password;
//        this.newPassword = newPassword;
//        this.phoneNumber = phoneNumber;
//        this.secondaryPhoneNumber = secondaryPhoneNumber;
//        this.businessNumber = businessNumber;
//        this.email = email;
//        this.accountNonLocked = accountNonLocked;
//        this.registrationDate = registrationDate;
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

}

//    user_id INT AUTO_INCREMENT PRIMARY KEY,                -- 고유 사용자 ID
//    username VARCHAR(50) NOT NULL UNIQUE,                 -- 아이디
//    name VARCHAR(50) NOT NULL,                             -- 이름
//    company_name VARCHAR(100),                             -- 상호명
//    password VARCHAR(255) NOT NULL,                        -- 해싱된 비밀번호
//    phone_number VARCHAR(15) NOT NULL,                    -- 전화번호
//    secondary_phone_number VARCHAR(15),                    -- 가게 전화번호
//    business_number VARCHAR(20) NOT NULL UNIQUE,       -- 사업자 번호
//    email VARCHAR(100) NOT NULL UNIQUE,                   -- 이메일
//    account_non_locked BOOLEAN DEFAULT TRUE,               -- 계정 잠금 여부
//    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP

