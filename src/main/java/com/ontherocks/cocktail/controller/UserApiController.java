package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController {

    private final UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/check-username")
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        boolean isAvailable = userService.isUsernameAvailable(username); // 사용자 서비스에서 아이디 중복 확인
        if (isAvailable) {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 아이디입니다.");
        }
    }
}
