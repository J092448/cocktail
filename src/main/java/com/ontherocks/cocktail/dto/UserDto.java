package com.ontherocks.cocktail.dto;

import java.time.LocalDateTime;

public class UserDto {
    private Integer userId;
    private String username;
    private String name;
    private String companyName;
    private String password; // 기존 비밀번호 (로그인 시 사용)
    private String newPassword; // 신규 비밀번호 (정보 수정 시 사용)
    private String phoneNumber;
    private String secondaryPhoneNumber;
    private String businessNumber;
    private String email;
    private boolean accountNonLocked;
    private LocalDateTime registrationDate;

    }



