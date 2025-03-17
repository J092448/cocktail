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
        // 관리자 계정 확인
        AdminDto admin = aDao.findByUsername(username);
        if (admin != null) {
            return User.builder()
                    .username(admin.getUsername())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        // 일반 사용자 계정 확인
        UserDto user = userMapper.getUser(username);
        if (user != null) {
            if (aSer.isSuspended(username)) { // 계정이 정지되었는지 확인
                throw new DisabledException("정지된 계정입니다."); // 예외 발생하여 로그인 차단
            }
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
    }
}
