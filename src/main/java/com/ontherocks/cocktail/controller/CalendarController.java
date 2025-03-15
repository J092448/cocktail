package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.model.CalendarDTO;
import com.ontherocks.cocktail.service.CalendarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // 특정 연월의 매출 데이터 가져오기
    @GetMapping("/sales")
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

    // 새로운 매출 데이터를 가져와서 캘린더 테이블에 삽입하는 엔드포인트
    @PostMapping("/import-sales")
    public ResponseEntity<String> importSalesData(
            @RequestParam(value = "year") int year,
            @RequestParam(value = "month") int month) {
        calendarService.importSalesData(year, month);
        return ResponseEntity.ok("Sales data imported successfully");
    }
}
