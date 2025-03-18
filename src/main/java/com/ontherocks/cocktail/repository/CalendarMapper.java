package com.ontherocks.cocktail.repository;

// 필요한 클래스 및 MyBatis 애노테이션 import

import com.ontherocks.cocktail.model.CalendarDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// MyBatis 매퍼 인터페이스로 선언
@Mapper
public interface CalendarMapper {

        // ✅ sales 테이블에서 날짜별로 데이터를 그룹화하여 가져오는 메서드
        @Select("SELECT sale_date AS date, SUM(total_amount) AS salesAmount " +
                "FROM sales " +
                "WHERE YEAR(sale_date) = #{year} AND MONTH(sale_date) = #{month} AND user_id = 11 " +
                "GROUP BY sale_date")
        List<CalendarDTO> getSalesData(@Param("year") int year, @Param("month") int month);
    /*
     - `@Select`: SQL SELECT 쿼리를 실행하여 데이터를 조회.
     - 쿼리 설명:
       - `SUM(total_amount)`: 같은 날짜(`sale_date`)에 해당하는 총 금액(`total_amount`)을 합산.
       - `YEAR(sale_date)`와 `MONTH(sale_date)`: 연도와 월을 조건으로 데이터를 필터링.
       - `user_id = 11`: 특정 사용자 ID(11)에 해당하는 데이터를 조회.
       - `GROUP BY sale_date`: 날짜(`sale_date`)를 기준으로 데이터를 그룹화.
     - 반환값: `CalendarDTO` 객체 리스트. 날짜(`date`)와 매출 합계(`salesAmount`)를 포함.
     */

        // ✅ calendar 테이블에 데이터를 삽입하는 메서드
        @Insert("INSERT INTO calendar (date, sales_amount) VALUES (#{date}, #{salesAmount})")
        void insertCalendarData(@Param("date") LocalDate date, @Param("salesAmount") BigDecimal salesAmount);
    /*
     - `@Insert`: SQL INSERT 쿼리를 실행하여 데이터를 삽입.
     - 입력값:
       - `#{date}`: 삽입할 날짜 필드.
       - `#{salesAmount}`: 삽입할 매출 금액 필드.
     - `@Param`: SQL 쿼리에 매개변수를 매핑.
     - 역할: `calendar` 테이블에 새로운 데이터 행을 삽입.
     */

        // ✅ calendar 테이블에서 특정 연도와 월의 데이터를 조회하는 메서드
        @Select("SELECT date, sales_amount FROM calendar WHERE YEAR(date) = #{year} AND MONTH(date) = #{month}")
        List<CalendarDTO> getCalendarData(@Param("year") int year, @Param("month") int month);
    /*
     - `@Select`: SQL SELECT 쿼리를 실행하여 데이터를 조회.
     - 쿼리 설명:
       - `YEAR(date)`와 `MONTH(date)`: 연도와 월 조건으로 데이터를 필터링.
       - 반환 컬럼: `date`, `sales_amount`.
     - 반환값: `CalendarDTO` 객체 리스트. 해당 연도와 월의 날짜와 매출 금액을 포함.
     */
}
