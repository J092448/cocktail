package com.ontherocks.tlqkf.service;

import com.ontherocks.tlqkf.model.Calendar;
import com.ontherocks.tlqkf.repository.CalendarMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarService {

    private final CalendarMapper calendarMapper;

    public CalendarService(CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    // 📌 연도 및 월별 매출 데이터 조회
    public Map<String, Object> getCalendarData(int year, int month) {
        List<Calendar> calendarEntries = calendarMapper.getEntriesByMonth(year, month);

        // ✅ 매출 데이터가 없을 경우 예외 처리
        BigDecimal totalSales = calendarEntries.stream()
                .map(Calendar::getSalesAmount) // ✅ BigDecimal 타입으로 변경
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("year", year);
        result.put("month", month);
        result.put("totalSales", totalSales); // ✅ 소수점 포함 매출액 반환

        return result;
    }
}
