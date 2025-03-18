package com.ontherocks.cocktail.service;

// 필요한 클래스 및 라이브러리 import

import com.ontherocks.cocktail.model.CalendarDTO;
import com.ontherocks.cocktail.repository.CalendarMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

// 이 클래스가 Spring의 Service임을 나타냄. 비즈니스 로직을 처리하는 역할을 담당
@Service
public class CalendarService {

    // 로깅용 Logger 객체 생성 (클래스의 이름을 로깅 출력의 식별자로 사용)
    private static final Logger logger = Logger.getLogger(CalendarService.class.getName());

    // CalendarMapper 의존성 (데이터베이스 접근을 담당)
    private final CalendarMapper calendarMapper;

    // 생성자를 통한 의존성 주입 (Spring이 CalendarMapper를 자동으로 주입)
    public CalendarService(CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    // ✅ 특정 연도와 월의 캘린더 데이터를 가져오는 메서드
    public List<CalendarDTO> getCalendarData(int year, int month) {
        // CalendarMapper를 사용해 데이터베이스에서 특정 연도와 월의 데이터를 조회하여 반환
        return calendarMapper.getCalendarData(year, month);
    }

    // ✅ 특정 연도와 월의 매출 데이터를 가져와 캘린더 테이블에 삽입하는 메서드
    public void importSalesData(int year, int month) {
        // `sales` 테이블에서 특정 연도와 월의 날짜별 매출 데이터를 조회
        List<CalendarDTO> groupedSalesData = calendarMapper.getSalesData(year, month);

        // 조회된 데이터에 대해 로그 출력
        groupedSalesData.forEach(data -> logger.info("Fetched data: " + data.toString()));

        // 데이터베이스에 삽입 처리 (groupedSalesData 리스트의 각 데이터를 순회)
        for (CalendarDTO data : groupedSalesData) {
            // 삽입 전 로그 출력
            logger.info("Inserting data: " + data.toString());
            // calendar 테이블에 데이터 삽입
            calendarMapper.insertCalendarData(data.getDate(), data.getSalesAmount());
        }
    }
}
