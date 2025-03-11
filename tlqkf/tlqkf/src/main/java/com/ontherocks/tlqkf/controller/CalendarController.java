package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.model.CalendarDTO;
import com.ontherocks.tlqkf.service.CalendarService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // 특정 연월의 매출 데이터 가져오기
    @GetMapping
    public List<CalendarDTO> getCalendarData(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        // 기본값: 현재 연도와 월
        if (year == null || month == null) {
            LocalDate today = LocalDate.now();
            year = (year == null) ? today.getYear() : year;
            month = (month == null) ? today.getMonthValue() : month;
        }

        return calendarService.getCalendarData(year, month);
    }
}
