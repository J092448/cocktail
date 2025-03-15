package com.ontherocks.cocktail.repository;

import com.ontherocks.cocktail.model.CalendarDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CalendarMapper {

        // sales 테이블에서 데이터 그룹화하여 가져오기
        @Select("SELECT sale_date AS date, SUM(total_amount) AS salesAmount " +
                "FROM sales " +
                "WHERE YEAR(sale_date) = #{year} AND MONTH(sale_date) = #{month} AND user_id = 11 " +
                "GROUP BY sale_date")
        List<CalendarDTO> getSalesData(@Param("year") int year, @Param("month") int month);

        // calendar 테이블에 데이터 삽입하기
        @Insert("INSERT INTO calendar (date, sales_amount) VALUES (#{date}, #{salesAmount})")
        void insertCalendarData(@Param("date") LocalDate date, @Param("salesAmount") BigDecimal salesAmount);

        // calendar 테이블에서 데이터 가져오기
        @Select("SELECT date, sales_amount FROM calendar WHERE YEAR(date) = #{year} AND MONTH(date) = #{month}")
        List<CalendarDTO> getCalendarData(@Param("year") int year, @Param("month") int month);
}
