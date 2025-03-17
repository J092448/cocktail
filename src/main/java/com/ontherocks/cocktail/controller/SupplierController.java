package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.CategoryDTO;
import com.ontherocks.cocktail.dto.ProductDTO;
import com.ontherocks.cocktail.dto.SupplierDTO;
import com.ontherocks.cocktail.service.CategoryService;
import com.ontherocks.cocktail.service.ProductService;
import com.ontherocks.cocktail.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SupplierController {

    @Autowired
    private CategoryService categoryService;
    private final SupplierService supplierService;

    @Autowired
    private ProductService productService; // 품목 서비스

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }


    @GetMapping("/newTradeCompany")
    public String showNewTradeCompanyForm(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories); // 카테고리 데이터를 모델에 추가
        return "pages/newTradeCompany"; // templates/newTradeCompany.html로 매핑
    }

    @GetMapping("/tradeCompanyList")
    public String getTradeCompanyList(@RequestParam(required = false) Integer categoryId, Model model) {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        for (SupplierDTO supplier : suppliers) {
            // 카테고리 이름을 설정
            if (supplier.getCategory() != null) {
                String categoryName = categoryService.getCategoryNameById(Integer.parseInt(supplier.getCategory()));
                supplier.setCategoryName(categoryName);
            } else {
                supplier.setCategoryName("기타"); // 기본값 설정
            }

            // 제품 정보 가져오기
            SupplierDTO supplierDetails = supplierService.getSupplierDetails(supplier.getSupplierId(), categoryId);
            supplier.setProducts(supplierDetails.getProducts()); // 품목 정보를 추가
        }
        model.addAttribute("suppliers", suppliers);
        return "pages/tradeCompanyList"; // Thymeleaf 템플릿 이름
    }



    @GetMapping("/tradeCompanyDetail")
    public String showCompanyDetail(@RequestParam Integer supplierId, Model model) {
        // 공급업체 정보를 가져옵니다.
        SupplierDTO supplier = supplierService.getSupplierDetails(supplierId, null); // null로 카테고리 ID 전달

        // 카테고리 이름 설정
        if (supplier.getCategory() != null) {
            String categoryName = categoryService.getCategoryNameById(Integer.parseInt(supplier.getCategory()));
            supplier.setCategoryName(categoryName);
        } else {
            supplier.setCategoryName("기타"); // 기본값 설정
        }

        // 제품 정보가 포함되어 있도록 설정
        List<ProductDTO> products = productService.getProductsBySupplierId(supplierId);
        supplier.setProducts(products); // SupplierDTO에 제품 리스트 추가

        // 모델에 supplier 추가
        model.addAttribute("supplier", supplier);

        return "pages/tradeCompanyDetail"; // 상세 정보 페이지로 매핑
    }





    @GetMapping("/tradeCompanyDetailEdit")
    public String showEditTradeCompanyForm(@RequestParam Integer supplierId, Model model) {
        // 공급업체 정보를 가져옵니다.
        SupplierDTO supplier = supplierService.getSupplierById(supplierId);

        // 카테고리 이름 설정
        if (supplier.getCategory() != null) {
            String categoryName = categoryService.getCategoryNameById(Integer.parseInt(supplier.getCategory()));
            supplier.setCategoryName(categoryName);
        } else {
            supplier.setCategoryName("기타"); // 기본값 설정
        }

        // 제품 정보 추가
        List<ProductDTO> products = productService.getProductsBySupplierId(supplierId);
        supplier.setProducts(products); // SupplierDTO에 제품 리스트 추가

        // 모델에 supplier 추가
        model.addAttribute("supplier", supplier);

        return "pages/tradeCompanyDetailEdit"; // 수정 폼 템플릿으로 매핑
    }




}
