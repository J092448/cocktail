package com.ontherocks.cocktail.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private UserDto user;
    private AdminDto admin;

    // UserDto를 위한 생성자
    public CustomUserDetails(UserDto user) {
        this.user = user;
        this.admin = null; // Admin은 null로 설정
    }

    // AdminDto를 위한 생성자
    public CustomUserDetails(AdminDto admin) {
        this.admin = admin;
        this.user = null; // User는 null로 설정
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (admin != null) {
            authorities.add(() -> "ROLE_ADMIN"); // Admin의 경우 권한 추가
        }
        if (user != null) {
            authorities.add(() -> "ROLE_USER"); // User의 경우 권한 추가
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return (user != null) ? user.getPassword() : admin.getPassword(); // User 또는 Admin의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return (user != null) ? user.getUsername() : admin.getUsername(); // User 또는 Admin의 사용자 이름 반환
    }

    public Integer getUserId() {
        return (user != null) ? user.getUserId() : null; // UserDto의 userId 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 필요 시 구현
    }

    @Override
    public boolean isAccountNonLocked() {
        return (user != null) ? user.isAccountNonLocked() : admin.isAccountNonLocked(); // User의 정지 여부 확인
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 필요 시 구현
    }

    @Override
    public boolean isEnabled() {
        return true; // 필요 시 구현
    }
}
