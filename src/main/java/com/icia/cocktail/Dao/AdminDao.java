package com.icia.cocktail.Dao;

import com.icia.cocktail.dto.AdminDto;
import com.icia.cocktail.dto.NoticeDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
//alt + enter [MybatisX]Generate mapper of xml
public interface AdminDao {
    //관리자 계정 생성
    void insertAdmin(AdminDto admin);
    //관리자 계정 조회
    AdminDto findByUsername(String username);
    //공지사항 작성
    boolean noticeWrite(NoticeDto notice);
    //팝업이 활성화 돼있고 만료기간 내에 있는 공지 표시
    List<NoticeDto> getActiveNotice();
}
