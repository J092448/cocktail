package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void registerUser(User user); // 사용자 등록
    User getUser(String username); // 사용자 조회
    boolean existsByUsername(String username); // 아이디 존재 여부 확인
    User findByDetails(String name, String phoneNumber, String businessNumber); // 아이디 찾기
    void updateUserPassword(User user); // 비밀번호 업데이트
    void updateUser(User user); // 사용자 정보 업데이트
}
