package com.ontherocks.tlqkf.repository;

import com.ontherocks.tlqkf.model.Calendar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CalendarMapper {

    // ✅ 특정 연도와 월의 매출 데이터 조회 (날짜순 정렬)
   @Select("SELECT date, sales_amount FROM calendar WHERE YEAR(date) = #{year} AND MONTH(date) = #{month} ORDER BY date ASC")
    List<Calendar> getEntriesByMonth(@Param("year") int year, @Param("month") int month);

}
