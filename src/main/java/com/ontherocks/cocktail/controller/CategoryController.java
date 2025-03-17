package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/name/{categoryName}")
    public ResponseEntity<Integer> getCategoryId(@PathVariable String categoryName) {
        Integer categoryId = categoryService.getCategoryIdByName(categoryName);
        if (categoryId != null) {
            return ResponseEntity.ok(categoryId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

