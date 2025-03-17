package com.ontherocks.cocktail;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ontherocks.cocktail.repository") // ✅ 추가
public class CocktailApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocktailApplication.class, args);
    }

}



