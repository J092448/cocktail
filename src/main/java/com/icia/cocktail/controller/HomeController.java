package com.icia.cocktail.controller;

import com.icia.cocktail.Dao.UserDao;
import com.icia.cocktail.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
private final PasswordEncoder passwordEncoder;
private final UserDao uDao;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Object msg = session.getAttribute("msg");
        log.info("홈 페이지에서 msg: {}", msg);
        if (session.getAttribute("msg") != null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }
        return "/index";
    }
    @GetMapping("/showNotice")
    public String showNotice() {
        return "/showNotice";
    }
}
