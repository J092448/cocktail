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

