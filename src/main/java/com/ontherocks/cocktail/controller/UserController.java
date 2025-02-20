package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.entity.User;
import com.ontherocks.cocktail.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login"; // /login으로 리다이렉트
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("msg", "로그인 실패: 사용자 이름 또는 비밀번호가 올바르지 않습니다.");
        }
        return "pages/login"; // 로그인 페이지로 이동
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        boolean success = userService.authenticate(username, password);
        if (success) {
            return "redirect:/dashboard"; // 대시보드로 리다이렉트
        } else {
            model.addAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다."); // alert에서 사용할 메시지
            return "pages/login"; // 로그인 페이지로 리다이렉트
        }
    }



    // 회원가입 페이지 표시
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "pages/register"; // 회원가입 페이지로 이동
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        // 비즈니스 번호에서 하이픈 제거
        String sanitizedBusinessNumber = user.getBusinessNumber().replace("-", "");
        user.setBusinessNumber(sanitizedBusinessNumber); // 하이픈 없는 값으로 설정

        try {
            userService.registerUser(user); // 사용자 등록
            return "redirect:/login?msg=회원가입이 완료되었습니다. 로그인 해주세요."; // 성공 메시지
        } catch (Exception e) {
            // 실패 메시지에는 하이픈이 포함되지 않도록 주의
            return "redirect:/register?error=회원가입 실패: " + e.getMessage(); // 실패 메시지
        }
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "pages/dashboard"; // 대시보드 페이지로 이동
    }

    @GetMapping("/findId")
    public String findId() {
        return "pages/findId"; // 아이디 찾기 페이지로 이동
    }

    @PostMapping("/findId")
    public String findId(@RequestParam String name, @RequestParam String phoneNumber, @RequestParam String businessNumber, RedirectAttributes redirectAttributes) {
        // 비즈니스 번호에서 하이픈 제거
        String sanitizedBusinessNumber = businessNumber.replace("-", "");

        // 사용자 아이디 찾기
        String username = userService.findUsername(name, phoneNumber, sanitizedBusinessNumber);

        if (username != null) {
            redirectAttributes.addFlashAttribute("msg", "아이디는: " + username + "입니다.");
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        } else {
            redirectAttributes.addFlashAttribute("error", "해당 정보로 등록된 아이디가 없습니다.");
            return "redirect:/findId"; // 아이디 찾기 페이지로 리다이렉트
        }
    }



    @GetMapping("/findPw")
    public String findPassword() {
        return "pages/findPw"; // 비밀번호 찾기 페이지로 이동
    }

    @PostMapping("/findPw")
    public String resetPassword(@RequestParam String username, @RequestParam String newPassword, Model model) {
        boolean success = userService.resetPassword(username, newPassword);
        if (success) {
            model.addAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
        } else {
            model.addAttribute("msg", "해당 아이디가 존재하지 않습니다.");
        }
        return "pages/findPw"; // 결과를 다시 비밀번호 찾기 페이지로 표시
    }

    @GetMapping("/confirmPw")
    public String confirmPw() {
        return "pages/confirmPw"; // 정보 수정 페이지(1차)로 이동
    }

    @PostMapping("/confirmPw")
    public String confirmPassword(@RequestParam String password, Model model) {
        // 현재 로그인한 사용자 정보 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);

        // 비밀번호 확인
        if (passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/editInfo"; // 비밀번호가 맞으면 정보 수정 페이지로 리다이렉트
        } else {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "pages/confirmPw"; // 비밀번호 확인 페이지로 다시 이동
        }
    }

    @GetMapping("/editInfo")
    public String editInfo(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user); // 현재 사용자 정보를 모델에 추가
        return "pages/editInfo"; // 정보 수정 페이지로 이동
    }

    @PostMapping("/editInfo")
    public String updateInfo(@ModelAttribute User user, @RequestParam String newPassword, Model model) {
        // 비밀번호가 입력되었는지 확인
        if (!newPassword.isEmpty()) {
            user.setPassword(newPassword); // 새로운 비밀번호 설정
        }
        userService.updateUserInfo(user); // 사용자 정보 업데이트
        model.addAttribute("msg", "정보가 성공적으로 수정되었습니다.");
        return "redirect:/dashboard"; // 수정 후 대시보드로 리다이렉트
    }
}
