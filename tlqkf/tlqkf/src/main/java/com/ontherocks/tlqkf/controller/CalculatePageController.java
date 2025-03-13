package com.ontherocks.tlqkf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalculatePageController {

    // ✅ `/calculate` 요청을 처리하여 `calculate.html` 반환
    @GetMapping("/calculate")
    public String showCalculatePage() {
        return "calculate"; // ✅ `src/main/resources/templates/calculate.html`을 렌더링
    }

}
