package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.UserDto;
import com.ontherocks.cocktail.mapper.UserMapper;
import com.ontherocks.cocktail.service.UserService;
import com.twilio.rest.verify.v2.service.Verification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;

import static com.ontherocks.cocktail.controller.PhoneNumberVerificationController.VERIFICATION_SERVICE_SID;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    private UserMapper userMapper; // UserMapper를 주입받음

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
    public String getLogin(Model model) {
        return "pages/login"; // 로그인 페이지로 이동
    }

//    @PostMapping("/login")
//    public String postLogin(@RequestParam String username, @RequestParam String password) {
//        boolean success = userService.authenticate(username, password);
//        if (success) {
//            return "redirect:/dashboard"; // 대시보드로 리다이렉트
//        } else {
//            return "redirect:/login?error=true"; // 로그인 실패 처리
//        }
//    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDto user) {
        // 입력된 비밀번호를 사용하여 로그인 처리
        UserDto existingUser = userService.getUser(user.getUsername());
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            // 로그인 성공
            return "redirect:/dashboard"; // 대시보드로 리다이렉트
        } else {
            // 로그인 실패 처리
            return "redirect:/login?error"; // 에러 처리
        }
    }





    // 로그아웃 처리
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "로그아웃이 완료되었습니다."); // 로그아웃 메시지 추가
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }








    // 회원가입 페이지 표시
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "pages/register"; // 회원가입 페이지로 이동
    }


    @PostMapping("/register")
    public String register(@ModelAttribute UserDto user) {
        // 비즈니스 번호에서 하이픈 제거
        String sanitizedBusinessNumber = user.getBusinessNumber().replace("-", "");
        user.setBusinessNumber(sanitizedBusinessNumber); // 하이픈 없는 값으로 설정

        try {
            userService.registerUser(user); // 사용자 등록
            return "redirect:/login?registerMsg"; // 성공 메시지
        } catch (Exception e) {
            // 실패 메시지에는 하이픈이 포함되지 않도록 주의
            return "redirect:/register?error"; // 실패 메시지
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
    @ResponseBody
    public ResponseEntity<String> findIdAjax(@RequestParam String name,
                                             @RequestParam String phoneNumber,
                                             @RequestParam String businessNumber) {
        // 비즈니스 번호에서 하이픈 제거
        String sanitizedBusinessNumber = businessNumber.replace("-", "");

        // 사용자 아이디 찾기
        String username = userService.findUsername(name, phoneNumber, sanitizedBusinessNumber);

        if (username != null) {
            return ResponseEntity.ok("아이디는: " + username + "입니다."); // 성공 메시지 반환
        } else {
            return ResponseEntity.badRequest().body("해당 정보로 등록된 아이디가 없습니다."); // 에러 메시지 반환
        }
    }




    @GetMapping("/findPw")
    public String findPassword() {
        return "pages/findPw"; // 비밀번호 찾기 페이지로 이동
    }

    @PostMapping("/findPw")
    public ResponseEntity<String> findPassword(@RequestParam String username, @RequestParam String name,
                                               @RequestParam String businessNumber, @RequestParam String phoneNumber) {
        // 비즈니스 번호에서 하이픈 제거
        String sanitizedBusinessNumber = businessNumber.replace("-", "");

        // 전화번호를 국제 형식으로 변환
        String formattedPhoneNumber = convertToInternationalFormat(phoneNumber);

        // 사용자 정보 검증
        boolean isValidUser = userService.validateUserInfo(username, name, sanitizedBusinessNumber, formattedPhoneNumber);

        if (!isValidUser) {
            return new ResponseEntity<>("입력한 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // OTP 생성 및 발송
        try {
            Verification.creator(VERIFICATION_SERVICE_SID, formattedPhoneNumber, "sms").create();
            System.out.println("OTP has been sent to " + formattedPhoneNumber);
            return new ResponseEntity<>("전화번호로 코드를 전송합니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("코드 전송 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 전화번호 변환 함수
    private String convertToInternationalFormat(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "+82" + phoneNumber.substring(1);
        }
        return phoneNumber; // 이미 국제 형식인 경우 그대로 반환
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String newPassword) {
        System.out.println("비밀번호 변경 요청 수신: " + username); // 로그 추가
        // 사용자 조회
        UserDto currentUser = userService.getUser(username); // UserService를 통해 사용자 조회
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 규약 검사 (서버 측에서도 확인)
        if (!isValidPassword(newPassword)) {
            return ResponseEntity.badRequest().body("비밀번호는 소문자, 숫자, 특수문자를 포함하고 8자 이상이어야 합니다.");
        }

        // 비밀번호 변경 요청
        userService.changePassword(username, newPassword); // 비밀번호 변경 메서드 호출

        // 성공 메시지 반환
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }



    // 비밀번호 유효성 검사 메서드
    private boolean isValidPassword(String password) {
        // 비밀번호 규약: 소문자, 숫자, 특수문자 포함, 8자 이상
        String passwordPattern = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-z0-9!@#$%^&*]{8,}$";
        return password.matches(passwordPattern);
    }




    @GetMapping("/confirmPw")
    public String confirmPw() {
        return "pages/confirmPw"; // 정보 수정 페이지(1차)로 이동
    }

    @PostMapping("/confirmPw")
    public String confirmPassword(@RequestParam String password, Model model) {
        // 현재 로그인한 사용자 정보 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.getUser(username);
        System.out.println("Username: " + user.getUsername()); // username 출력

        // 비밀번호 확인
        if (passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/editInfo"; // 비밀번호가 맞으면 정보 수정 페이지로 리다이렉트
        } else {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "pages/confirmPw"; // 비밀번호 확인 페이지로 다시 이동
        }
    }


@GetMapping("/editInfo")
public String editInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if (userDetails == null) {
        return "redirect:/login";
    }

    // UserDto로 변환
    UserDto user = userMapper.getUser(userDetails.getUsername());

    model.addAttribute("user", user);
    return "pages/editInfo";
}


@PostMapping("/editInfo")
public String updateInfo(@ModelAttribute UserDto user, Model model) {
    // 현재 로그인한 사용자의 username 가져오기
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    // 사용자 정보 가져오기
    UserDto currentUser = userService.getUser(username);

    // 신규 비밀번호가 입력된 경우에만 처리
    if (user.getNewPassword() != null && !user.getNewPassword().isEmpty()) {
        // 새로운 비밀번호 해시화
        String hashedPassword = passwordEncoder.encode(user.getNewPassword());
        currentUser.setPassword(hashedPassword); // 해시된 비밀번호 설정
    }

// 이메일 업데이트 (이메일이 변경되지 않은 경우 기존 이메일 유지)
    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
        currentUser.setEmail(user.getEmail()); // 이메일 업데이트
    } else {
        currentUser.setEmail(currentUser.getEmail()); // 기존 이메일 유지
    }


    // 비밀번호 외의 다른 정보 업데이트
    currentUser.setPhoneNumber(user.getPhoneNumber()); // 전화번호 업데이트
    currentUser.setSecondaryPhoneNumber(user.getSecondaryPhoneNumber()); // 가게 전화번호 업데이트 (필요한 경우)

    // 사용자 정보 업데이트
    userService.updateUserInfo(currentUser); // user 객체에 비밀번호가 포함되어 있음
    return "redirect:/dashboard?infoMsg"; // 수정 후 대시보드로 리다이렉트
}


}
