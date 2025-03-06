package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.AdminDto;
import com.ontherocks.cocktail.dto.UserDto;
import com.ontherocks.cocktail.mapper.AdminDao;
import com.ontherocks.cocktail.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminDao aDao;
    @Autowired
    private AdminService aSer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userMapper.getUser(username);
        AdminDto admin = aDao.findByUsername(username);
        String role;
        if (admin != null) {
            role = "ADMIN";
            return User.builder()
                    .username(admin.getUsername())
                    .password(admin.getPassword())
                    .roles(role).build();
        }
        if (user != null) {
            if (aSer.isSuspended(username)){ //정지 여부 확인
                throw new DisabledException("정지된 계정입니다."); //예외 발생시켜 로그인 차단
            }
            role = "USER";
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(role).build();
        }
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);

//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.isAccountNonLocked(),
//                true, // 계정 만료 여부
//                true, // 비밀번호 만료 여부
//                true, // 계정 활성화 여부
//                new ArrayList<>() // 권한 목록 (필요시 추가)
//        );
    }
}
