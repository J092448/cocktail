package com.ontherocks.cocktail.Dao;

import com.ontherocks.cocktail.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    //업체 계정 조회
    UserDto findByUsername(String username);
}
