package com.ontherocks.cocktail.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CalendarDTO {
    private LocalDate date;
    private BigDecimal salesAmount;


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

}
