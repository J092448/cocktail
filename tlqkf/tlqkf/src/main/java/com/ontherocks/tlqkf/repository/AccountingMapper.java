package com.ontherocks.tlqkf.repository;

import com.ontherocks.tlqkf.model.AccountingDataDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountingMapper {

    @Select("SELECT * FROM accounting_with_revenue WHERE date = CURRENT_DATE")
    AccountingDataDTO getCurrentAccountingData();

    @Insert("INSERT INTO accounting (date, product_sales, salary, transportation_cost, office_supplies_cost, rent_expense) " +
            "VALUES (#{date}, #{productSales}, #{salary}, #{transportationCost}, #{officeSuppliesCost}, #{rentExpense})")
    void saveAccountingData(AccountingDataDTO accountingData);
}
