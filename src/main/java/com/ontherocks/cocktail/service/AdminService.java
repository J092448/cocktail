package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.Dao.AdminDao;
import com.ontherocks.cocktail.common.Paging;
import com.ontherocks.cocktail.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
private final PasswordEncoder passwordEncoder;
private final AdminDao aDao;

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

    public List<Suspended_usersDto> getUserList(SearchDto search) {
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
}
