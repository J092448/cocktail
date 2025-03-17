package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.ProductDTO;
import com.ontherocks.cocktail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> getProductsByCategoryId(@RequestParam Integer categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }



    @PostMapping("/products") // 품목 추가
    public void addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
    }

    @PutMapping("/products/{id}") // 품목 수정
    public void updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        productDTO.setId(id); // ID를 설정하여 업데이트
        productService.updateProduct(productDTO);
    }

    @DeleteMapping("/products/{id}") // 품목 삭제
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}



