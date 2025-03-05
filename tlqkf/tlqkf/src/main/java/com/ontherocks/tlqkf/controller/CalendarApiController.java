package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.service.CalendarService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // CORS 허용
public class CalendarApiController {
    private final CalendarService calendarService;

    public CalendarApiController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // 📌 특정 연도와 월의 매출 데이터 조회 API
    @GetMapping("/sales")
    public Map<String, Object> getCalendarData(@RequestParam int year, @RequestParam int month) {
        return calendarService.getCalendarData(year, month); // ✅ 오타 수정
    }
}
