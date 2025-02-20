package com.icia.cocktail.Dao;

import com.icia.cocktail.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    //업체 계정 조회
    UserDto findByUsername(String username);
}
