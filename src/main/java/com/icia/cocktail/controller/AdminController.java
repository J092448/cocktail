package com.icia.cocktail.controller;

import com.icia.cocktail.dto.AdminDto;
import com.icia.cocktail.dto.NoticeDto;
import com.icia.cocktail.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final AdminService aSer;

    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        if (session.getAttribute("msg") != null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }
        return "admin/main";
    }
    @GetMapping("/info") //관리자 계정 정보 조회
    public String info(Principal principal, Model model) {
        String username = principal.getName();
        AdminDto admin = aSer.getAdminInfo(username);
        if (admin != null) {
            model.addAttribute("admin", admin);
        }
        model.addAttribute("notFound", "계정이 존재하지 않습니다.");
        return "admin/info";
    }
    @GetMapping("/notice")
    public String notice() {
        return "admin/notice";
    }
    @PostMapping("/notice")
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
}
