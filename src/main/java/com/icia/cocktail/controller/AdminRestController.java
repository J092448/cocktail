package com.icia.cocktail.controller;

import com.icia.cocktail.dto.NoticeDto;
import com.icia.cocktail.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {
    private final AdminService aSer;

    @GetMapping("/showNotice")
    public List<NoticeDto> showNotice() {
        return aSer.getActiveNotice();
    }
    @PostMapping("/suspendUser")
    public ResponseEntity<String> suspendUser(String username) {
        aSer.suspendUser(username);
        return ResponseEntity.ok("계정이 정지되었습니다.");
    }
    @PostMapping("/activateUser")
    public ResponseEntity<String> activateUser(String username) {
        aSer.activateUser(username);
        return ResponseEntity.ok("정지가 해제되었습니다.");
    }
}
