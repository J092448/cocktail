package com.ontherocks.cocktail.Dao;

import com.ontherocks.cocktail.dto.*;
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
    //관리자 정보 수
    void updateInfo(AdminDto admin);
    //전체 유저 목록 조회
    List<Suspended_usersDto> getUserList(SearchDto search);
    //전체 업체 수 & 검색 결과로 나온 업체
    int getUserCnt(SearchDto search);
    //계정 정지
    void suspendUser(String username);
    //정지 여부 확인
    boolean isSuspendedUser(String username);
    //계정 정지 해제
    void activateUser(String username);
    //전체 공지 조회
    List<NoticeDto> getNoticeList(SearchDto search);
    //전체 공지 수 & 검색 결과로 나온 공지 수
    int getNoticeCnt(SearchDto search);
}
