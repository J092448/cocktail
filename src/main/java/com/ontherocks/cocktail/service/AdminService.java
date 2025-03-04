package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.Dao.AdminDao;
import com.ontherocks.cocktail.Dao.UserDao;
import com.ontherocks.cocktail.common.Paging;
import com.ontherocks.cocktail.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
private final PasswordEncoder passwordEncoder;
private final AdminDao aDao;
private final UserDao uDao;

    public AdminService(PasswordEncoder passwordEncoder, AdminDao aDao, UserDao uDao) {
        this.passwordEncoder = passwordEncoder;
        this.aDao = aDao;
        this.uDao = uDao;
    }

    public void createAdmin() { //관리자 회원가입 구현 없이 db에 직접 계정 생성
    AdminDto admin = new AdminDto();
    admin.setUsername("admin"); //아이디
    admin.setPassword(passwordEncoder.encode("1234")); //해싱된 비밀번호
    admin.setEmail("admin@test.com"); //이메일
    admin.setTel("01012345678"); //전화번호
    aDao.insertAdmin(admin);
}
    @EventListener(ApplicationReadyEvent.class) //모든 Bean 완전히 실행되고 이 메소드 실행
    public void initAdmin() {
        // db에 데이터가 존재하는지 확인
        if (aDao.findByUsername("admin") == null) { // "admin"이라는 username이 없으면 실
            createAdmin();
        }
    }
    public AdminDto getAdminInfo(String username) {
        return aDao.findByUsername(username);
    }

    public boolean noticeWrite(NoticeDto notice) {
        return aDao.noticeWrite(notice);
    }

    public List<NoticeDto> getActiveNotice() {
        return aDao.getActiveNotice();
    }

    public void updateInfo(AdminDto admin) {
        aDao.updateInfo(admin);
    }

    public List<UserDto> getUserList(SearchDto search) {
        Integer pageNum = search.getPageNum(); //현재 페이지 번호
        //시작인덱스 = (현재 페이지 번호 - 1) * 페이지 당 글 개수
        search.setStartIdx((pageNum - 1) * search.getListCnt());
        return aDao.getUserList(search);
    }

    public String getUserPaging(SearchDto search) {
        int totalNum = aDao.getUserCnt(search); //전체 업체 수, 검색 결과로 나온 업체
        if (totalNum == 0) {
            return "";
        } //리스트가 없으면 빈 문자열 반환
        String listUrl = null;
        if (search.getColName() != null) { //검색 중일 때
            listUrl = "admin/sellerList?colName=" + search.getColName()
                    + "&keyword=" + search.getKeyword() + "&";
        }else { //검색 중이 아닐 때
            listUrl = "admin/sellerList?";
        }
        Paging paging = new Paging(totalNum, search.getPageNum(), search.getListCnt(), 10, listUrl);
        return paging.makePaging();
    }

    public void suspendUser(String username) {
        aDao.suspendUser(username); //suspended_users 테이블에 저장
    }

    public void activateUser(String username) {
        aDao.activateUser(username);//suspended_users 테이블에서 삭제
    }

    public List<NoticeDto> getNoticeList(SearchDto search) {
        search.setStartIdx((search.getPageNum() - 1) * search.getListCnt());
        return aDao.getNoticeList(search);
    }

    public String getNoticePaging(SearchDto search) {
        int totalNum = aDao.getNoticeCnt(search);
        String listUrl = null;
        if (search.getColName() != null) {
            listUrl = "admin/noticeList?colName=" + search.getColName()
                    + "&keyword=" + search.getKeyword() + "&";
        }else {
            listUrl = "admin/noticeList?";
        }
        Paging paging = new Paging(totalNum, search.getPageNum(), search.getListCnt(), 10, listUrl);
        return paging.makePaging();
    }

    @EventListener
    public void onLoginSuccess(AuthenticationSuccessEvent event) {
        //로그인에 성공하면 @EventListener 감지하여 이 메소드 실행
        String username = event.getAuthentication().getName();
        UserDto user = uDao.findByUsername(username);
        AdminDto admin = aDao.findByUsername(username);
        if (admin != null) {
            return; //관리자 계정으로 로그인하면 저장하지 않음
        }
        int user_id = user.getUser_id();
        if (user == null) { //users 테이블에 없으면 저장하지 않음
            return;
        }
        // 최근 로그인 기록 확인 후 중복 저장 방지
        if (!aDao.hasRecentLogin(user_id)) {
            aDao.saveLoginHistory(user_id);
        }
    }
    @EventListener(ApplicationReadyEvent.class)
    public void runOnStartup() {
        int yesterdayVisitors = aDao.getYesterdayVisitors(); //어제 방문자 수
        int yesterdaySignups = aDao.getYesterdaySignups(); //어제 가입자 수
        DailyStatisticsDto stats = new DailyStatisticsDto();
        LocalDate yesterday = LocalDate.now().minusDays(1); //어제 날짜
        stats.setDate(yesterday); stats.setTotal_visitors(yesterdayVisitors); stats.setNew_users(yesterdaySignups);
        aDao.save(stats); //서버가 실행되면 테이블에 저장
        aDao.missingdate(); //최근 7일 이내 누락된 날짜가 있으면 기본값(0) 넣기
    }

    public boolean isSuspended(String username) {
        UserDto user = uDao.findByUsername(username);
        if (user == null) {
            return false;
        } //users 테이블에 없으면 false 반환
        return user.getAccount_non_locked() == 0; //값이 0(정지된 유저)이면 true 반환
    }
}
