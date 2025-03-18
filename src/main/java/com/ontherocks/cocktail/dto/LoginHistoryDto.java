package com.ontherocks.cocktail.dto;

import java.time.LocalDate;

public class LoginHistoryDto {
    private int id;
    private int user_id;
    private LocalDate login_date;

    public LoginHistoryDto(int id, int user_id, LocalDate login_date) {
        this.id = id;
        this.user_id = user_id;
        this.login_date = login_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDate getLogin_date() {
        return login_date;
    }

    public void setLogin_date(LocalDate login_date) {
        this.login_date = login_date;
    }
}
