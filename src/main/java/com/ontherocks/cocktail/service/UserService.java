package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.UserDto;
import com.ontherocks.cocktail.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper; // UserMapper 인스턴스 주입

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인스턴스 주입

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void registerUser(UserDto user) {
        user.setPassword(encodePassword(user.getPassword())); // 비밀번호 암호화
        userMapper.registerUser(user); // 사용자 등록
    }

    public UserDto getUser(String username) {
        return userMapper.getUser(username); // 사용자 조회
    }

    public boolean isUsernameAvailable(String username) {
        return !userMapper.existsByUsername(username); // 아이디 중복 확인
    }

    // 아이디 찾기
    public String findUsername(String name, String phoneNumber, String businessNumber) {
        UserDto user = userMapper.findByDetails(name, phoneNumber, businessNumber);
        return user != null ? user.getUsername() : null; // 사용자 아이디 반환 또는 null
    }


    @Transactional
    public void updateUserInfo(UserDto user) {
        System.out.println("Updating user: " + user); // 로그 추가
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Phone Number: " + user.getPhoneNumber());
        System.out.println("Secondary Phone Number: " + user.getSecondaryPhoneNumber());
        System.out.println("Email: " + user.getEmail());
        // 현재 사용자 정보 가져오기
        UserDto existingUser = userMapper.getUser(user.getUsername());


        // 신규 비밀번호가 입력된 경우에만 해싱
        if (user.getNewPassword() != null && !user.getNewPassword().isEmpty()) {
            user.setPassword(encodePassword(user.getNewPassword())); // 새로운 비밀번호 해시화
        }
        // 이메일이 변경된 경우 유니크성 검사
        if (isEmailChanged(existingUser, user)) {
            if (existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다."); // 예외 처리
            }
        }

        // 사용자 정보 업데이트
        userMapper.updateUser(user); // 사용자 정보 업데이트


    }

    // 이메일 변경 여부 확인
    private boolean isEmailChanged(UserDto existingUser, UserDto user) {
        return !existingUser.getEmail().equals(user.getEmail());
    }

    // 이메일 존재 여부 확인
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    // 전화번호 변환 메서드
    public String convertToInternationalFormat(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "+82" + phoneNumber.substring(1); // 0을 제거하고 +82 추가
        }
        return phoneNumber; // 이미 국제 형식인 경우 그대로 반환
    }

    // 비밀번호 찾기 (사용자 정보 검증 로직)
    public boolean validateUserInfo(String username, String name, String businessNumber, String phoneNumber) {
        UserDto user = getUser(username); // 사용자 정보를 가져옴
        if (user != null) {
            // DB에서 가져온 전화번호 변환
            String dbPhoneNumber = convertToInternationalFormat(user.getPhoneNumber()); // DB 전화번호를 변환

            // 입력된 정보와 비교
            return user.getName().equals(name) &&
                    user.getBusinessNumber().equals(businessNumber) &&
                    dbPhoneNumber.equals(phoneNumber);
        }
        return false; // 사용자 정보가 없거나 일치하지 않는 경우
    }


    @Transactional
    public void changePassword(String username, String newPassword) {
        System.out.println("비밀번호 변경 요청 시작: " + username); // 로그 추가
        // 사용자 조회
        UserDto user = userMapper.getUser(username);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 해시 처리
        String hashedPassword = encodePassword(newPassword);
        user.setPassword(hashedPassword); // 해시된 비밀번호 설정

        // 비밀번호 업데이트 메서드 호출
        System.out.println("업데이트할 비밀번호: " + user.getPassword()); // 로그 추가
        try {
            userMapper.updateUserPassword(user);
            System.out.println("비밀번호 업데이트 완료");
        } catch (Exception e) {
            System.err.println("비밀번호 업데이트 중 오류 발생: " + e.getMessage());
            throw e; // 예외를 다시 던져서 트랜잭션 롤백

        }


    }
}

