package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.service.CalendarService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/sales")
    public int getTotalSales() {
        return calendarService.calculateTotalSales();
    }
}
