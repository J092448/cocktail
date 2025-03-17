package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.CocktailIngredientDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CocktailIngredientMapper {
    List<CocktailIngredientDTO> getCocktailIngredients();
    CocktailIngredientDTO getIngredientById(Integer ingredientId);
}
