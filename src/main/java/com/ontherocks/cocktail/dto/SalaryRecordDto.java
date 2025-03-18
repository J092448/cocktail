package com.ontherocks.cocktail.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryRecordDto {
    private Integer employeeId; // 직원 ID
    private BigDecimal amount;   // 지급된 급여
    private LocalDate paymentDate; // 지급 날짜

    // Getters and Setters
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
