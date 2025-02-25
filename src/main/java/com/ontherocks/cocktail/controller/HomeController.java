package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.Dao.UserDao;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
private final PasswordEncoder passwordEncoder;
private final UserDao uDao;

    public HomeController(PasswordEncoder passwordEncoder, UserDao uDao) {
        this.passwordEncoder = passwordEncoder;
        this.uDao = uDao;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Object msg = session.getAttribute("msg");
//        log.info("홈 페이지에서 msg: {}", msg);
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
