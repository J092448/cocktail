package com.icia.cocktail.controller;

import com.icia.cocktail.dto.AdminDto;
import com.icia.cocktail.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService aSer;

    @GetMapping("/main")
    public String main() {
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
}
