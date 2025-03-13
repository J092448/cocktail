package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.model.CalendarDTO;
import com.ontherocks.tlqkf.service.CalendarService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // 특정 연월의 매출 데이터 가져오기
    @GetMapping("/sales") // ✅ 변경
    public List<CalendarDTO> getCalendarData(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        if (year == null || month == null) {
            LocalDate today = LocalDate.now();
            year = today.getYear();
            month = today.getMonthValue();
        }

        return calendarService.getCalendarData(year, month);
    }

    // 일정 페이지를 반환하는 메소드 (예: calendar.html)
    @GetMapping("/calendar")
    public String showCalendarPage() {
        return "calendar";  // 해당 HTML 페이지 이름을 반환
    }
}
