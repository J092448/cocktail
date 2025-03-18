package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.CategoryDTO;
import com.ontherocks.cocktail.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 모든 카테고리 가져오기
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    // 카테고리 ID로 카테고리 이름 가져오기
    public String getCategoryNameById(int categoryId) {
        List<CategoryDTO> categories = getAllCategories();
        for (CategoryDTO category : categories) {
            if (category.getCategoryId() == categoryId) {
                return category.getCategoryName();
            }
        }
        return "기타"; // 해당 ID의 카테고리가 없을 경우 기본값
    }

    // 카테고리 이름으로 카테고리 ID 가져오기
    public Integer getCategoryIdByName(String categoryName) {
        List<CategoryDTO> categories = getAllCategories();
        for (CategoryDTO category : categories) {
            if (category.getCategoryName().equals(categoryName)) {
                return category.getCategoryId(); // 해당 이름의 카테고리 ID 반환
            }
        }
        return null; // 해당 이름의 카테고리가 없을 경우 null 반환
    }
}
