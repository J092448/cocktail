package com.ontherocks.tlqkf.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Calendar {
    private LocalDate date;       // ✅ MySQL `DATE`와 매칭
    private BigDecimal salesAmount; // ✅ `DECIMAL(15,2)`와 매칭
}
