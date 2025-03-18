package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.CocktailIngredientDTO;
import com.ontherocks.cocktail.service.CocktailIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CocktailIngredientController {
    @Autowired
    private CocktailIngredientService cocktailIngredientService;

    @GetMapping("/cocktail-ingredients")
    public List<CocktailIngredientDTO> getCocktailIngredients() {
        return cocktailIngredientService.getCocktailIngredients();
    }
}
