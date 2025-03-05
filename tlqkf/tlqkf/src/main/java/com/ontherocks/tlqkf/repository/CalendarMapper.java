package com.ontherocks.tlqkf.repository;

import com.ontherocks.tlqkf.model.Calendar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CalendarMapper {

    // ✅ 전체 매출 데이터 조회 (날짜순 정렬)
    @Select("SELECT id, event_name, date, sales FROM calendar ORDER BY date ASC")
    List<Calendar> getAllEntries();
}
