package com.ontherocks.cocktail.controller;

// 필요한 클래스 및 라이브러리 import

import com.ontherocks.cocktail.model.CalendarDTO;
import com.ontherocks.cocktail.service.CalendarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// REST 컨트롤러로 선언. 주로 JSON 형식의 응답을 처리.
@RestController
// 모든 엔드포인트의 기본 URI를 "/api/calendar"로 설정.
@RequestMapping("/api/calendar")
public class CalendarController {

    // CalendarService 의존성을 주입받음 (서비스 계층에서 비즈니스 로직 처리).
    private final CalendarService calendarService;

    // 생성자를 통해 의존성 주입 (Spring에서 자동 관리).
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // 특정 연도와 월의 매출 데이터를 가져오는 엔드포인트
    @GetMapping("/sales")
    public List<CalendarDTO> getCalendarData(
            // 연도와 월을 쿼리 파라미터로 받음. 필수 항목이 아니므로 제공되지 않을 경우 null로 처리.
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        // 연도와 월이 null일 경우 현재 연도와 월로 기본값 설정
        if (year == null || month == null) {
            LocalDate today = LocalDate.now(); // 현재 날짜 가져오기
            year = today.getYear(); // 현재 연도
            month = today.getMonthValue(); // 현재 월
        }

        // CalendarService를 호출하여 해당 연도와 월의 매출 데이터를 가져옴
        return calendarService.getCalendarData(year, month);
    }


    // 새로운 매출 데이터를 가져와 캘린더 테이블에 삽입하는 엔드포인트
    @PostMapping("/import-sales")
    public ResponseEntity<String> importSalesData(
            // 연도와 월을 요청 파라미터로 받음 (필수 항목).
            @RequestParam(value = "year") int year,
            @RequestParam(value = "month") int month) {
        // 서비스 계층에서 매출 데이터를 가져와 데이터베이스에 삽입
        calendarService.importSalesData(year, month);

        // 성공 메시지를 반환하며 HTTP 200 상태를 설정
        return ResponseEntity.ok("Sales data imported successfully");
    }
}
