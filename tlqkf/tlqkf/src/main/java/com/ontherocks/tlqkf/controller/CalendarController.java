package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.service.CalendarService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/sales")
    public BigDecimal getTotalSales() {
        return calendarService.calculateTotalSales(); // ✅ 반환 타입을 `BigDecimal`로 변경
    }
}
