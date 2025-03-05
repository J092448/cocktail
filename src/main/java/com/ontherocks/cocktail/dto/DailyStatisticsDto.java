package com.ontherocks.cocktail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DailyStatisticsDto {
    private LocalDate date;
    private int total_visitors;
    private int new_users;

//    public DailyStatisticsDto(LocalDate date, int total_visitors, int new_users) {
//        this.date = date;
//        this.total_visitors = total_visitors;
//        this.new_users = new_users;
//    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotal_visitors() {
        return total_visitors;
    }

    public void setTotal_visitors(int total_visitors) {
        this.total_visitors = total_visitors;
    }

    public int getNew_users() {
        return new_users;
    }

    public void setNew_users(int new_users) {
        this.new_users = new_users;
    }
}
