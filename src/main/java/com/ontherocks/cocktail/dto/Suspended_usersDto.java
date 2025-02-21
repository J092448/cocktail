package com.ontherocks.cocktail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suspended_usersDto {
    private String username;
    private String name;
    private String company_name;
    private LocalDateTime registration_date;
    private LocalDateTime suspended_at;
}
