package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.model.Calculate;
import com.ontherocks.tlqkf.service.CalculateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // ✅ CORS 허용
@Controller  // 🚨 RestController → Controller 변경
@RequestMapping("/calculate")
public class CalculateController {
    private final CalculateService calculateService;

    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    // ✅ calculate 페이지 요청 처리 (Thymeleaf로 이동)
    @GetMapping
    public String showCalculatePage() {
        return "calculate"; // 👉 templates/calculate.html로 연결됨
    }

}
