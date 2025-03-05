package com.ontherocks.tlqkf.repository;

import com.ontherocks.tlqkf.model.Calculate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CalculateMapper {

    // 🔹 calendar 테이블에서 월별 총 매출 조회
    @Select("SELECT SUM(sales) AS totalSales, DATE_FORMAT(date, '%Y-%m') AS month FROM calendar GROUP BY month")
    List<Calculate> findAllSales();

    // 🔹 ordering 테이블에서 월별 총 원가 조회
    @Select("SELECT SUM(cost) AS totalCost, DATE_FORMAT(date, '%Y-%m') AS month FROM ordering GROUP BY month")
    List<Calculate> findAllCosts();
}
