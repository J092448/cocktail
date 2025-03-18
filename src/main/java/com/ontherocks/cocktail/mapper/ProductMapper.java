package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductDTO> getProductsBySupplierId(Integer supplierId);

    List<ProductDTO> getProductsBySupplierIdAndCategoryId(Integer supplierId, Integer categoryId);

    List<ProductDTO> getProductsByCategoryId(Integer categoryId); // 기존 메서드

    void addProduct(ProductDTO productDTO); // 품목 추가

    void updateProduct(ProductDTO productDTO); // 품목 수정

    void deleteProduct(Integer id); // 품목 삭제

    ProductDTO getProductById(Integer id); // Spirit
    ProductDTO getProductByIdFromMixers(Integer id); // Mixer
    ProductDTO getProductByIdFromFruits(Integer id); // Fruit
    ProductDTO getProductByIdFromBeverages(Integer id); // Beverage
}
