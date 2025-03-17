package com.ontherocks.cocktail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ontherocks.cocktail.dto.IngredientDTO;
import com.ontherocks.cocktail.mapper.IngredientMapper;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientMapper ingredientMapper;

    // 재료 추가
    public void createIngredient(IngredientDTO ingredient) {
        ingredientMapper.insertIngredient(ingredient);
    }

    // 모든 재료 조회
    public List<IngredientDTO> getAllIngredients() {
        return ingredientMapper.selectAllIngredients();
    }

    // 특정 재료 조회
    public IngredientDTO getIngredientById(Integer id) {
        return ingredientMapper.selectIngredientById(id);
    }
}
