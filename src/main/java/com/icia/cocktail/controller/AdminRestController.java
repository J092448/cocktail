package com.icia.cocktail.controller;

import com.icia.cocktail.dto.NoticeDto;
import com.icia.cocktail.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
