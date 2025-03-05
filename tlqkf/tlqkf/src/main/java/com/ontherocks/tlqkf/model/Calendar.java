package com.ontherocks.tlqkf.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Calendar {
    private BigDecimal salesAmount;
    private int id;           // 고유 ID (PK)
    private String eventName; // 이벤트 이름
    private String date;      // 날짜 (String or LocalDate)
    private int sales;        // 매출액 필드 추가
}
