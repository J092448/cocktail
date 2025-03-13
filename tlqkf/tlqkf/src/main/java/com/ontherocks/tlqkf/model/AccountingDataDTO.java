package com.ontherocks.tlqkf.model;

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
    private BigDecimal grossProfit;
    private BigDecimal sgAndA;
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


        // ✅ Getter 및 Setter 추가
        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }


