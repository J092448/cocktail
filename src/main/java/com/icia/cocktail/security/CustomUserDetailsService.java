package com.icia.cocktail.security;

import com.icia.cocktail.Dao.AdminDao;
import com.icia.cocktail.Dao.UserDao;
import com.icia.cocktail.dto.AdminDto;
import com.icia.cocktail.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminDao aDao;
    @Autowired
    private UserDao uDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminDto admin = aDao.findByUsername(username);
        UserDto user = uDao.findByUsername(username);
        String role;
        if (admin != null) {
            role = "ADMIN";
            return User.builder().username(admin.getUsername())
                    .password(admin.getPassword()).roles(role).build();
        }
        if (user != null) {
            if (aDao.isSuspendedUser(username)) { //정지 여부 확인
                throw new DisabledException("정지된 계정입니다."); //예외 발생시켜 로그인 차단
            }
            role = "USER";
            return User.builder().username(user.getUsername())
                    .password(user.getPassword()).roles(role).build();
        }
        log.error("유저를 찾을 수 없음: {}", username);
        throw new UsernameNotFoundException("유저를 찾을 수 없음" + username);
    }
}
