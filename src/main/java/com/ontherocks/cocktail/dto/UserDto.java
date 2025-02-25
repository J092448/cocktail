package com.ontherocks.cocktail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int user_id;
    private String username;
    private String name;
    private String company_name;
    private String password;
    private String phone_number;
    private String secondary_phone_number;
    private String business_number;
    private String email;
    private int account_non_locked;
    private LocalDateTime registration_date;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSecondary_phone_number() {
        return secondary_phone_number;
    }

    public void setSecondary_phone_number(String secondary_phone_number) {
        this.secondary_phone_number = secondary_phone_number;
    }

    public String getBusiness_number() {
        return business_number;
    }

    public void setBusiness_number(String business_number) {
        this.business_number = business_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccount_non_locked() {
        return account_non_locked;
    }

    public void setAccount_non_locked(int account_non_locked) {
        this.account_non_locked = account_non_locked;
    }

    public LocalDateTime getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDateTime registration_date) {
        this.registration_date = registration_date;
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
}
