package com.ontherocks.tlqkf.service;

import com.ontherocks.tlqkf.model.Calendar;
import com.ontherocks.tlqkf.repository.CalendarMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalendarService {

    private final CalendarMapper calendarMapper;

    public CalendarService(CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public int calculateTotalSales() {
        List<Calendar> calendarEntries = calendarMapper.getAllEntries();

        // ✅ 매출 데이터가 없을 경우 예외 처리
        if (calendarEntries == null || calendarEntries.isEmpty()) {
            return 0; // 데이터가 없으면 0 반환
        }

        // ✅ 매출액 합산 (getSales()가 정상적으로 동작해야 함)
        return calendarEntries.stream().mapToInt(Calendar::getSales).sum();
    }
}
