package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.CocktailIngredientDTO;
import com.ontherocks.cocktail.mapper.CocktailIngredientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CocktailIngredientService {
    @Autowired
    private CocktailIngredientMapper cocktailIngredientMapper;

    public List<CocktailIngredientDTO> getCocktailIngredients() {
        return cocktailIngredientMapper.getCocktailIngredients();
    }

}
