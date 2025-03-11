package com.ontherocks.tlqkf.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountingDataDTO {
    private LocalDate date;
    private BigDecimal productSales;
    private BigDecimal salary;
    private BigDecimal transportationCost;
    private BigDecimal officeSuppliesCost;
    private BigDecimal rentExpense;
}
