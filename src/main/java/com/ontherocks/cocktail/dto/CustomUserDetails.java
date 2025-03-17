package com.ontherocks.cocktail.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private UserDto user;

    public CustomUserDetails(UserDto user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); // 필요한 경우 권한 추가
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Integer getUserId() {
        return user.getUserId(); // userId 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 필요 시 구현
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
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
