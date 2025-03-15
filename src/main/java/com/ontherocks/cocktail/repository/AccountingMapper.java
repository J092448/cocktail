package com.ontherocks.cocktail.repository;

import com.ontherocks.cocktail.model.Accounting;
import com.ontherocks.cocktail.model.AccountingDataDTO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AccountingMapper {

    // ✅ 현재 연도와 월에 해당하는 데이터 가져오기
    @Select("SELECT * FROM accounting WHERE YEAR(date) = #{year} AND MONTH(date) = #{month}")
    List<Accounting> getAccountingData(@Param("year") int year, @Param("month") int month);

    // 현재 월 데이터 가져오기
    @Select("SELECT * FROM accounting_with_revenue WHERE YEAR(date) = YEAR(CURDATE()) AND MONTH(date) = MONTH(CURDATE())")
    AccountingDataDTO getCurrentAccountingData();

    // 이전 월 데이터 가져오기
    @Select("SELECT * FROM accounting_with_revenue WHERE YEAR(date) = #{year} AND MONTH(date) = #{month}")
    AccountingDataDTO getPreviousAccountingData(@Param("year") int year, @Param("month") int month);

    // ✅ 상품 매출원가(자동) 조회 (order 테이블에서 총액을 합산하여 가져옴)
    @Select("SELECT SUM(total_price) FROM orders WHERE YEAR(order_date) = YEAR(CURDATE()) AND MONTH(order_date) = MONTH(CURDATE())")
    BigDecimal getProductCostAuto();

    // ✅ 데이터가 존재하면 업데이트, 없으면 삽입 (동적 쿼리 사용)
    @Insert("INSERT INTO accounting (date, user_id, sales, cost_of_sales, product_cost_auto, product_cost_manual, beginning_inventory, ending_inventory, gross_profit, sg_and_a, salary, transportation_cost, office_supplies_cost, rent_expense, operating_income, non_operating_income, non_operating_expense, pre_tax_income, tax_expense, net_income, month, year, product_sales) " +
            "VALUES (#{date}, #{userId}, #{sales}, #{costOfSales}, #{productCostAuto}, #{productCostManual}, #{beginningInventory}, #{endingInventory},#{grossProfit}, #{sgAndA}, #{salary}, #{transportationCost}, #{officeSuppliesCost}, #{rentExpense}, #{operatingIncome}, #{nonOperatingIncome}, #{nonOperatingExpense}, #{preTaxIncome}, #{taxExpense}, #{netIncome}, #{month}, #{year}, #{productSales}) " +
            "ON DUPLICATE KEY UPDATE sales = VALUES(sales), cost_of_sales = VALUES(cost_of_sales), product_cost_auto = VALUES(product_cost_auto), product_cost_manual = VALUES(product_cost_manual), beginning_inventory = VALUES(beginning_inventory), ending_inventory = VALUES(ending_inventory), gross_profit = VALUES(gross_profit), sg_and_a = VALUES(sg_and_a), salary = VALUES(salary), transportation_cost = VALUES(transportation_cost), office_supplies_cost = VALUES(office_supplies_cost), rent_expense = VALUES(rent_expense), operating_income = VALUES(operating_income), non_operating_income = VALUES(non_operating_income), non_operating_expense = VALUES(non_operating_expense), pre_tax_income = VALUES(pre_tax_income), tax_expense = VALUES(tax_expense), net_income = VALUES(net_income), month = VALUES(month), year = VALUES(year), product_sales = VALUES(product_sales)")
    void saveOrUpdateAccountingData(AccountingDataDTO accountingDataDTO);

}




