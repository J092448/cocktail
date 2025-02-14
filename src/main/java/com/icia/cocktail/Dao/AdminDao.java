package com.icia.cocktail.Dao;

import com.icia.cocktail.dto.AdminDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//alt + enter [MybatisX]Generate mapper of xml
public interface AdminDao {
    //관리자 계정 생성
    void insertAdmin(AdminDto admin);
    //관리자 계정 조회
    AdminDto findByUsername(String username);


}
