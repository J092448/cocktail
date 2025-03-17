package com.ontherocks.cocktail.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AccountingDataDTO {
    private Long accountingId;
    private Integer year;
    private Integer month;
    private BigDecimal sales;
    private BigDecimal costOfSales;
    private BigDecimal productCostAuto;
    private BigDecimal productCostManual;
    private BigDecimal beginningInventory;
    private BigDecimal endingInventory;
    private BigDecimal grossProfit; // 추가된 필드
    private BigDecimal sgAndA; // 추가된 필드
    private BigDecimal salary;
    private BigDecimal transportationCost;
    private BigDecimal officeSuppliesCost;
    private BigDecimal rentExpense;
    private BigDecimal operatingIncome;
    private BigDecimal nonOperatingIncome;
    private BigDecimal nonOperatingExpense;
    private BigDecimal preTaxIncome;
    private BigDecimal taxExpense;
    private BigDecimal netIncome;
    private Integer userId;
    private BigDecimal productSales;

    private BigDecimal salesPrevious;
    private BigDecimal costOfSalesPrevious;
    private BigDecimal productCostAutoPrevious;
    private BigDecimal productCostManualPrevious;
    private BigDecimal beginningInventoryPrevious;
    private BigDecimal endingInventoryPrevious;
    private BigDecimal grossProfitPrevious;
    private BigDecimal sgAndAPrevious;
    private BigDecimal salaryPrevious;
    private BigDecimal transportationCostPrevious;
    private BigDecimal officeSuppliesCostPrevious;
    private BigDecimal rentExpensePrevious;
    private BigDecimal operatingIncomePrevious;
    private BigDecimal nonOperatingIncomePrevious;
    private BigDecimal nonOperatingExpensePrevious;
    private BigDecimal preTaxIncomePrevious;
    private BigDecimal taxExpensePrevious;
    private BigDecimal netIncomePrevious;

    private LocalDate date; // 추가: date 필드

    @Override
public String toString() {
    return "AccountingDataDTO{" +
            "date=" + date +
            ", userId=" + userId +
            ", sales=" + sales +
            ", costOfSales=" + costOfSales +
            ", productCostManual=" + productCostManual +
            ", beginningInventory=" + beginningInventory +
            ", endingInventory=" + endingInventory +
            ", grossProfit=" + grossProfit +
            ", sgAndA=" + sgAndA +
            ", salary=" + salary +
            ", transportationCost=" + transportationCost +
            ", officeSuppliesCost=" + officeSuppliesCost +
            ", rentExpense=" + rentExpense +
            ", operatingIncome=" + operatingIncome +
            ", nonOperatingIncome=" + nonOperatingIncome +
            ", nonOperatingExpense=" + nonOperatingExpense +
            ", preTaxIncome=" + preTaxIncome +
            ", taxExpense=" + taxExpense +
            ", netIncome=" + netIncome +
            ", month=" + month +
            ", year=" + year +
            ", productSales=" + productSales +
            '}';
}


}
