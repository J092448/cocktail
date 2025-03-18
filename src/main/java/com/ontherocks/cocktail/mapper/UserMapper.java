package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void registerUser(UserDto user); // 사용자 등록
    UserDto getUser(String username); // 사용자 조회
    boolean existsByUsername(String username); // 아이디 존재 여부 확인
    UserDto findByDetails(String name, String phoneNumber, String businessNumber); // 아이디 찾기
    void updateUserPassword(UserDto user); // 비밀번호 업데이트
    void updateUser(UserDto user); // 사용자 정보 업데이트
    // 이메일 존재 여부 확인 쿼리
    boolean existsByEmail(String email);
}
