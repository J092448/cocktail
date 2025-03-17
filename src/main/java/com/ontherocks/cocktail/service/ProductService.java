package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.ProductDTO;
import com.ontherocks.cocktail.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
@Autowired
private ProductMapper productMapper;

public ProductDTO getProductById(Integer id) {
    // Spirit 제품 조회
    ProductDTO product = productMapper.getProductById(id);
    if (product != null) {
        return product; // Spirit 제품 반환
    }

    // Mixer 제품 조회
    product = productMapper.getProductByIdFromMixers(id);
    if (product != null) {
        return product; // Mixer 제품 반환
    }

    // Fruit 제품 조회
    product = productMapper.getProductByIdFromFruits(id);
    if (product != null) {
        return product; // Fruit 제품 반환
    }

    // Beverage 제품 조회
    product = productMapper.getProductByIdFromBeverages(id);
    if (product != null) {
        return product; // Beverage 제품 반환
    }

    // 모든 제품에서 찾지 못한 경우 null 반환
    return null;
}





public List<ProductDTO> getProductsByCategoryId(Integer categoryId) {
    List<ProductDTO> products = productMapper.getProductsByCategoryId(categoryId);
    for (ProductDTO product : products) {
        switch (categoryId) {
            case 1: // 주류
                product.setProductType("spirit");
                product.setSpiritId(product.getId()); // spiritId 설정
                product.setFruitId(null); // 다른 ID는 null로 설정
                product.setBeverageId(null);
                product.setMixerId(null);
                break;
            case 2: // 과일
                product.setProductType("fruit");
                product.setFruitId(product.getId()); // fruitId 설정
                product.setSpiritId(null); // 다른 ID는 null로 설정
                product.setBeverageId(null);
                product.setMixerId(null);
                break;
            case 3: // 음료
                product.setProductType("beverage");
                product.setBeverageId(product.getId()); // beverageId 설정
                product.setSpiritId(null);
                product.setFruitId(null);
                product.setMixerId(null);
                break;
            case 4: // 믹서
                product.setProductType("mixer");
                product.setMixerId(product.getId()); // mixerId 설정
                product.setSpiritId(null);
                product.setFruitId(null);
                product.setBeverageId(null);
                break;
            default:
                product.setProductType("unknown"); // 기본값 설정
                product.setSpiritId(null);
                product.setFruitId(null);
                product.setBeverageId(null);
                product.setMixerId(null);
                break;
        }
    }
    return products;
}

    public String getProductNameByIngredientId(Integer ingredientId) {
        ProductDTO product = productMapper.getProductById(ingredientId); // ingredientId로 품목 조회
        return product != null ? product.getName() : "품목 없음"; // 품목 이름 반환
    }




    public void addProduct(ProductDTO productDTO) {
    productMapper.addProduct(productDTO);
}

public void updateProduct(ProductDTO productDTO) {
    productMapper.updateProduct(productDTO);
}

public void deleteProduct(Integer id) {
    productMapper.deleteProduct(id);
}

// 기존의 공급업체 ID 관련 메서드 추가
public List<ProductDTO> getProductsBySupplierId(Integer supplierId) {
    return productMapper.getProductsBySupplierId(supplierId);
}
//    public List<ProductDTO> getProductsBySupplierIdAndCategoryId(Integer supplierId, Integer categoryId) {
//        return productMapper.getProductsBySupplierIdAndCategoryId(supplierId, categoryId);
//    }
public String getProductNameByCategoryId(Integer categoryId) {
    List<ProductDTO> products = productMapper.getProductsByCategoryId(categoryId);
    return products.isEmpty() ? "품목 없음" : products.get(0).getName(); // 품목 이름 반환
}

}
