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
    List<UserDto> getUserList(SearchDto search);
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
    //공지 보기
    NoticeDto getDetailNotice(int noticeNum);
    //공지 수정
    void updateNotice(NoticeDto notice);
    //공지 삭제
    void deleteNotice(int noticeNum);
    //업체회원이 로그인 시 테이블에 저장
    void saveLoginHistory(int user_id);
    //오늘의 방문자 수 조회
    int getTodayVisitor();
    //오늘의 가입자 수 조회
    int getTodaySignup();
    //최근 로그인 기록 확인
    boolean hasRecentLogin(int userId);
    //어제의 방문자 수
    int getYesterdayVisitors();
    //어제의 가입자
    int getYesterdaySignups();
    //어제의 가입자, 방문자 수 저장
    void save(DailyStatisticsDto stats);
    //최근 7일 이내 누락된 날짜가 있으면 기본값(0) 넣기
    void missingdate();
    //일주일 간 가입자, 방문자 수 조회
    List<DailyStatisticsDto> getWeeklyStats();
}
