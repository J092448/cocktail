package com.icia.cocktail.service;

import com.icia.cocktail.Dao.AdminDao;
import com.icia.cocktail.dto.AdminDto;
import com.icia.cocktail.dto.NoticeDto;
import jakarta.annotation.PostConstruct;
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
}
