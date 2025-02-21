package com.ontherocks.cocktail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminDto {
    private String username;
    private String password;
    private String email;
    private String tel;
    private String status;
    private String role;
}
