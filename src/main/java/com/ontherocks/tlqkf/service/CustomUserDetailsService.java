package com.ontherocks.tlqkf.service;

import com.ontherocks.tlqkf.model.User;
import com.ontherocks.tlqkf.repository.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    // ✅ 생성자 주입 방식으로 변경
    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // 암호화된 비밀번호 사용
                .roles(user.getRole()) // ROLE_USER, ROLE_ADMIN 등 역할 부여
                .build();
    }
}
