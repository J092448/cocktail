package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.model.CalendarDTO;
import com.ontherocks.cocktail.repository.CalendarMapper;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import java.util.List;

@Service
public class CalendarService {

    private static final Logger logger = Logger.getLogger(CalendarService.class.getName());
    private final CalendarMapper calendarMapper;

    public CalendarService(CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public List<CalendarDTO> getCalendarData(int year, int month) {
        return calendarMapper.getCalendarData(year, month);
    }

    public void importSalesData(int year, int month) {
        List<CalendarDTO> groupedSalesData = calendarMapper.getSalesData(year, month);

        // 데이터 로그 출력
        groupedSalesData.forEach(data -> logger.info("Fetched data: " + data.toString()));

        for (CalendarDTO data : groupedSalesData) {
            logger.info("Inserting data: " + data.toString());
            calendarMapper.insertCalendarData(data.getDate(), data.getSalesAmount());
        }
    }
}