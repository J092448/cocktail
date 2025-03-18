package com.ontherocks.cocktail.repository;

// 필요한 클래스 및 어노테이션 import

import com.ontherocks.cocktail.model.Accounting;
import com.ontherocks.cocktail.model.AccountingDataDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

// MyBatis 매퍼 인터페이스임을 나타냄
@Mapper
public interface AccountingMapper {

    // ✅ 현재 연도와 월에 해당하는 회계 데이터를 가져오는 메서드
    // @Select 어노테이션: 데이터베이스에서 쿼리를 실행하여 데이터를 조회
    @Select("SELECT * FROM accounting WHERE YEAR(date) = #{year} AND MONTH(date) = #{month}")
    List<Accounting> getAccountingData(@Param("year") int year, @Param("month") int month);
    /*
     - `@Select`는 SQL SELECT 쿼리를 실행.
     - year와 month를 파라미터로 받아 해당 연도와 월의 데이터를 가져옴.
     - `@Param` 어노테이션은 SQL에서 사용할 파라미터 매핑.
    */

    // ✅ 현재 월의 회계 데이터를 조회
    @Select("SELECT * FROM accounting_with_revenue WHERE YEAR(date) = YEAR(CURDATE()) AND MONTH(date) = MONTH(CURDATE())")
    AccountingDataDTO getCurrentAccountingData();
    /*
     - `@Select`는 현재 연도와 월에 해당하는 데이터를 `accounting_with_revenue` 테이블에서 가져옴.
     - CURDATE()는 현재 날짜를 가져오는 MySQL 함수.
    */

    // ✅ 특정 연도와 월의 이전 회계 데이터를 조회
    @Select("SELECT * FROM accounting_with_revenue WHERE YEAR(date) = #{year} AND MONTH(date) = #{month}")
    AccountingDataDTO getPreviousAccountingData(@Param("year") int year, @Param("month") int month);
    /*
     - `@Select`는 특정 연도와 월에 대한 회계 데이터를 가져옴.
     - 파라미터 매핑을 위해 `@Param`을 사용.
    */

    // ✅ 상품 매출원가(자동) 조회
    @Select("SELECT SUM(total_price) FROM orders WHERE YEAR(order_date) = YEAR(CURDATE()) AND MONTH(order_date) = MONTH(CURDATE())")
    BigDecimal getProductCostAuto();
    /*
     - `@Select`는 `orders` 테이블에서 총 주문 금액(`total_price`)을 합산하여 반환.
     - 현재 연도와 월에 해당하는 데이터만 조회.
     - SUM() 함수는 합계를 계산.
    */

    // ✅ 데이터 삽입 또는 업데이트 (ON DUPLICATE KEY UPDATE를 사용한 동적 쿼리)
    @Insert("INSERT INTO accounting (date, user_id, sales, cost_of_sales, product_cost_auto, product_cost_manual, beginning_inventory, ending_inventory, gross_profit, sg_and_a, salary, transportation_cost, office_supplies_cost, rent_expense, operating_income, non_operating_income, non_operating_expense, pre_tax_income, tax_expense, net_income, month, year, product_sales) " +
            "VALUES (#{date}, #{userId}, #{sales}, #{costOfSales}, #{productCostAuto}, #{productCostManual}, #{beginningInventory}, #{endingInventory},#{grossProfit}, #{sgAndA}, #{salary}, #{transportationCost}, #{officeSuppliesCost}, #{rentExpense}, #{operatingIncome}, #{nonOperatingIncome}, #{nonOperatingExpense}, #{preTaxIncome}, #{taxExpense}, #{netIncome}, #{month}, #{year}, #{productSales}) " +
            "ON DUPLICATE KEY UPDATE sales = VALUES(sales), cost_of_sales = VALUES(cost_of_sales), product_cost_auto = VALUES(product_cost_auto), product_cost_manual = VALUES(product_cost_manual), beginning_inventory = VALUES(beginning_inventory), ending_inventory = VALUES(ending_inventory), gross_profit = VALUES(gross_profit), sg_and_a = VALUES(sg_and_a), salary = VALUES(salary), transportation_cost = VALUES(transportation_cost), office_supplies_cost = VALUES(office_supplies_cost), rent_expense = VALUES(rent_expense), operating_income = VALUES(operating_income), non_operating_income = VALUES(non_operating_income), non_operating_expense = VALUES(non_operating_expense), pre_tax_income = VALUES(pre_tax_income), tax_expense = VALUES(tax_expense), net_income = VALUES(net_income), month = VALUES(month), year = VALUES(year), product_sales = VALUES(product_sales)")
    void saveOrUpdateAccountingData(AccountingDataDTO accountingDataDTO);
    /*
     - `@Insert`를 사용하여 데이터를 삽입하거나 업데이트.
     - ON DUPLICATE KEY UPDATE:
       - 데이터가 이미 존재하는 경우 해당 필드를 업데이트.
       - 새로운 데이터인 경우 INSERT 실행.
     - VALUES() 함수는 INSERT 값들을 재사용하여 UPDATE에 활용.
    */
}
