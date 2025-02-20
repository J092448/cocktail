package com.icia.cocktail.controller;

import com.icia.cocktail.Dao.AdminDao;
import com.icia.cocktail.Dao.UserDao;
import com.icia.cocktail.dto.*;
import com.icia.cocktail.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final AdminService aSer;
    private final UserDao uDao;
    private final AdminDao aDao;

    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        if (session.getAttribute("msg") != null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }
        return "admin/main";
    }
    @GetMapping("/info") //관리자 계정 정보 조회
    public String info(Principal principal, Model model, HttpSession session) {
        String username = principal.getName();
        AdminDto admin = aSer.getAdminInfo(username);
        if (admin != null) {
            String tel = admin.getTel() //전화번호에 - 추가
                    .replaceFirst("(\\d{3})(\\d{4})(\\d{3})", "$1-$2-$3");;
            model.addAttribute("tel", tel);
            model.addAttribute("admin", admin);
        }
        if (session.getAttribute("msg") != null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }
        return "admin/info";
    }
    @PostMapping("/info") //관리자 정보 수정
    public String updateInfo(AdminDto admin, RedirectAttributes rttr) {
        aSer.updateInfo(admin);
        rttr.addFlashAttribute("msg","수정되었습니다.");
        return "redirect:/admin/info";
    }

    @GetMapping("/notice") //공지사항 작성 페이지 이동
    public String notice() {
        return "admin/notice";
    }
    @PostMapping("/notice") //공지사항 작성
    public String noticeWrite(NoticeDto notice, RedirectAttributes rttr) {
        log.info("notice: {}", notice);
        boolean result = aSer.noticeWrite(notice);
        if(result){
            rttr.addFlashAttribute("msg","공지사항을 작성했습니다.");
            return "redirect:/admin/main";
        }else {
            rttr.addFlashAttribute("msg","공지사항 작성에 실패했습니다.");
            return "redirect:/admin/notice";
        }
    }
    @GetMapping("/sellerList")
    public String sellerList(SearchDto search, Model model, HttpSession session) { //업체 목록
        if(search.getPageNum() == null || search.getPageNum() < 1){
            search.setPageNum(1);
        } //페이지 번호를 요청하지 않거나 음수를 넣으면 1페이지로 설정

        if(search.getListCnt() == null){
            search.setListCnt(10);
        } //한 페이지 당 글 개수 설정
        if (search.getStartIdx() == null) {
            search.setStartIdx(0);
        } //시작인덱스 설정
        log.info("=======search: " + search);
        List<Suspended_usersDto> uList = null;
        uList = aSer.getUserList(search); //전체 유저 조회(suspended_users 테이블과 조인해서 정지 여부까지 확인)

        log.info("=======list: " + uList);
        if (uList != null) { //리스트가 있으면 페이징
            String pageHtml = aSer.getUserPaging(search);
            if (search.getKeyword() != null) { //검색 중이면 세션에 검색 정보 저장
                session.setAttribute("search", search);
            }else { //검색 중이 아니면 페이지 번호만 저장
                session.removeAttribute("search");
                session.setAttribute("pageNum", search.getPageNum());
            }
            model.addAttribute("uList", uList);
            model.addAttribute("pageHtml", pageHtml);
            return "admin/sellerList";
        }
        return "admin/sellerList";
    }
    @GetMapping("/detailInfo")
    public String detailInfo(Model model, HttpSession session, String username) {
        UserDto user = uDao.findByUsername(username);
        boolean suspendedUser = aDao.isSuspendedUser(username);
        model.addAttribute("suspendedUser", suspendedUser);
        log.info("user: {}", user);
        // 번호에 - 추가 \\d{3}: 3자리 숫자 (...): 그룹 지정 $1: 첫번째 그룹 -: 하이픈
        String pn = user.getPhone_number()
                .replaceFirst("(\\d{3})(\\d{4})(\\d{3})", "$1-$2-$3");
        String bin = user.getBusiness_number()
                .replaceFirst("(\\d{3})(\\d{2})(\\d{5})", "$1-$2-$3");
        model.addAttribute("pn", pn);
        model.addAttribute("bin", bin);
        model.addAttribute("user", user);
        String secPn = user.getSecondary_phone_number();
        if (secPn != null) { // null 체크 추가
            String sPn = null;
            if (secPn.length() == 10) { //10자리 일 때
                sPn = secPn.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
            } else if (secPn.length() == 11) { //11자리 일 때
                sPn = secPn.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3"); // 11자리 번호에 맞는 정규 표현식 사용
            }
            if (sPn != null) { // 변환된 전화번호가 있을 경우에만 model에 추가
                model.addAttribute("sPn", sPn);
            }
        }
        return "/admin/detailInfo";
    }
}
