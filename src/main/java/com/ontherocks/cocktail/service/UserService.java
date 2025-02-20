package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.entity.User;
import com.ontherocks.cocktail.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper; // UserMapper 인스턴스 주입

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인스턴스 주입

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void registerUser(User user) {
        user.setPassword(encodePassword(user.getPassword())); // 비밀번호 암호화
        userMapper.registerUser(user); // 사용자 등록
    }

    public User getUser(String username) {
        return userMapper.getUser(username); // 사용자 조회
    }

    public boolean isUsernameAvailable(String username) {
        return !userMapper.existsByUsername(username); // 아이디 중복 확인
    }

    // 아이디 찾기
    public String findUsername(String name, String phoneNumber, String businessNumber) {
        User user = userMapper.findByDetails(name, phoneNumber, businessNumber);
        return user != null ? user.getUsername() : null; // 사용자 아이디 반환 또는 null
    }

    // 비밀번호 찾기
    public boolean resetPassword(String username, String newPassword) {
        User user = userMapper.getUser(username);
        if (user != null) {
            user.setPassword(encodePassword(newPassword)); // 새로운 비밀번호 암호화
            userMapper.updateUserPassword(user); // 비밀번호 업데이트
            return true; // 성공적으로 비밀번호 변경
        }
        return false; // 사용자 없음
    }

    public void updateUserInfo(User user) {
        if (user.getPassword() != null) {
            user.setPassword(encodePassword(user.getPassword())); // 비밀번호 암호화
        }
        userMapper.updateUser(user); // 사용자 정보 업데이트
    }

    public boolean authenticate(String username, String password) {
        // 인증 로직 구현
        User user = userMapper.getUser(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword()); // 비밀번호 확인
        }
        return false; // 사용자 없음
    }
}

