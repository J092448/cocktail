package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.IngredientDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IngredientMapper {

    // 재료 추가
    void insertIngredient(IngredientDTO ingredient);

    // 모든 재료 조회
    List<IngredientDTO> selectAllIngredients();

    // 특정 재료 조회
    IngredientDTO selectIngredientById(Integer id);
}
