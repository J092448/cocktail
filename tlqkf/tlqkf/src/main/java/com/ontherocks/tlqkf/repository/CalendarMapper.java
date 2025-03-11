package com.ontherocks.tlqkf.repository;

import com.ontherocks.tlqkf.model.CalendarDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CalendarMapper {

    @Select("SELECT date, sales_amount FROM calendar WHERE YEAR(date) = #{year} AND MONTH(date) = #{month}")
    List<CalendarDTO> getCalendarData(int year, int month);
}
