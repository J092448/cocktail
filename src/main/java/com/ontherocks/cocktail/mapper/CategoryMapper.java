package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<CategoryDTO> getAllCategories();
}
