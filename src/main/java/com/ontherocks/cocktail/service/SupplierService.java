package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.ProductDTO;
import com.ontherocks.cocktail.dto.SupplierDTO;
import com.ontherocks.cocktail.mapper.ProductMapper;
import com.ontherocks.cocktail.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService  productService;

    @Autowired
    private CategoryService categoryService; // CategoryService 주입

    public List<SupplierDTO> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierMapper.findAll();
        for (SupplierDTO supplier : suppliers) {
            // 카테고리 이름을 설정
            supplier.setCategoryName(categoryService.getCategoryNameById(Integer.parseInt(supplier.getCategory())));
        }
        return suppliers;
    }

    public SupplierDTO getSupplierById(Integer id) {
        SupplierDTO supplierDTO = supplierMapper.findById(id);
        // 카테고리 ID를 설정
        if (supplierDTO.getCategory() != null) {
            try {
                int categoryId = Integer.parseInt(supplierDTO.getCategory());
                String categoryName = categoryService.getCategoryNameById(categoryId);
                supplierDTO.setCategoryName(categoryName);
            } catch (NumberFormatException e) {
                System.err.println("유효하지 않은 카테고리 ID: " + supplierDTO.getCategory());
                supplierDTO.setCategoryName("기타");
            }
        } else {
            supplierDTO.setCategoryName("기타");
        }
        return supplierDTO;
    }

    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        supplierDTO.setRegistrationDate(LocalDate.now());
        if (supplierDTO.getCategoryName() != null) {
            Integer categoryId = categoryService.getCategoryIdByName(supplierDTO.getCategoryName());
            supplierDTO.setCategory(categoryId.toString());
        }
        supplierMapper.save(supplierDTO);
        return supplierDTO;
    }

    public SupplierDTO updateSupplier(Integer id, SupplierDTO supplierDTO) {
        SupplierDTO existingSupplier = supplierMapper.findById(id);
        if (existingSupplier != null) {
            supplierDTO.setSupplierId(id);
            supplierDTO.setUpdatedDate(LocalDate.now());

            // 카테고리 이름을 카테고리 ID로 변환
            if (supplierDTO.getCategoryName() != null) {
                Integer categoryId = categoryService.getCategoryIdByName(supplierDTO.getCategoryName());
                supplierDTO.setCategory(categoryId.toString()); // 카테고리 ID로 설정
            }

            supplierMapper.update(supplierDTO);
            return supplierMapper.findById(id);
        }
        return null;
    }



    public boolean deleteSupplier(Integer id) {
        SupplierDTO existingSupplier = supplierMapper.findById(id);
        if (existingSupplier != null) {
            supplierMapper.deleteById(id);
            return true;
        }
        return false;
    }

    public SupplierDTO getSupplierDetails(Integer supplierId, Integer categoryId) {
        SupplierDTO supplier = supplierMapper.findById(supplierId);
        if (supplier != null) {
            List<ProductDTO> products;
            // 카테고리 ID가 제공된 경우 해당 카테고리의 제품을 가져옵니다.
            if (categoryId != null) {
                products = productMapper.getProductsBySupplierIdAndCategoryId(supplierId, categoryId);
            } else {
                // 카테고리 ID가 없으면 제품 목록을 가져옵니다.
                products = productService.getProductsBySupplierId(supplierId);
            }
            supplier.setProducts(products); // 품목 정보를 추가
            System.out.println("Supplier ID: " + supplierId + ", Products: " + products);
        }
        return supplier;
    }



}
