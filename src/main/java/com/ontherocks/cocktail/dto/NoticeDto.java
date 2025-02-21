package com.ontherocks.cocktail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private int notice_num;
    private String notice_title;
    private String notice_contents;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDate deadline;
    private String status;
    private LocalDateTime created_date;
}
