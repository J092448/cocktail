package com.ontherocks.tlqkf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/previousData")
public class PreviousDataController {

    @GetMapping
    public Map<String, Object> getPreviousData() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "✅ 이전 월 데이터 응답 성공");
        return response;
    }
}
