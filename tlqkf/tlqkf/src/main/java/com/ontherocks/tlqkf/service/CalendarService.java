package com.ontherocks.tlqkf.service;

import com.ontherocks.tlqkf.model.CalendarDTO;
import com.ontherocks.tlqkf.repository.CalendarMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    private final CalendarMapper calendarMapper;

    public CalendarService(CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public List<CalendarDTO> getCalendarData(int year, int month) {
        return calendarMapper.getCalendarData(year, month);
    }
}
