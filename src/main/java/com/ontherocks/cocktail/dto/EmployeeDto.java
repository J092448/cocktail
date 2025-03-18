package com.ontherocks.cocktail.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDto {
    private Integer employeeId;         // 고유 직원 ID
    private Integer userId;             // 사용자 ID
    private String employeeName;     // 직원 이름
    private BigDecimal hourlyWage;   // 시급
    private BigDecimal totalHoursWorked = BigDecimal.ZERO; // 총 일한 시간
    private LocalDate lastResetDate; // 마지막 리셋 날짜

    // Getters and Setters
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public BigDecimal getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(BigDecimal hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public BigDecimal getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(BigDecimal totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public LocalDate getLastResetDate() {
        return lastResetDate;
    }

    public void setLastResetDate(LocalDate lastResetDate) {
        this.lastResetDate = lastResetDate;
    }
}

