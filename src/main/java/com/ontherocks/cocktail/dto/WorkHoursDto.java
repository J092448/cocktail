package com.ontherocks.cocktail.dto;

import java.math.BigDecimal;

public class WorkHoursDto {
    private String employeeName; // 직원 이름 추가
    private BigDecimal hoursWorked;

    // Getters and Setters
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(BigDecimal hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
