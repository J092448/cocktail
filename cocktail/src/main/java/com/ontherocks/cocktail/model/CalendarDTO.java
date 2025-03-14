package com.ontherocks.cocktail.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CalendarDTO {
    private LocalDate date;
    private BigDecimal salesAmount;
}
