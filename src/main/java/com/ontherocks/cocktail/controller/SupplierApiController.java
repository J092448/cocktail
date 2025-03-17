package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.ProductDTO;
import com.ontherocks.cocktail.dto.SupplierDTO;
import com.ontherocks.cocktail.dto.SupplierProductsDTO;
import com.ontherocks.cocktail.service.ProductService;
import com.ontherocks.cocktail.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierApiController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Integer id) {
        SupplierDTO supplier = supplierService.getSupplierById(id);
        if (supplier != null) {
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        supplierDTO.setRegistrationDate(LocalDate.now());
        SupplierDTO createdSupplier = supplierService.createSupplier(supplierDTO);
        return ResponseEntity.ok(createdSupplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Integer id, @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO updatedSupplier = supplierService.updateSupplier(id, supplierDTO);
        if (updatedSupplier != null) {
            return ResponseEntity.ok(updatedSupplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        boolean isDeleted = supplierService.deleteSupplier(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{supplierId}/products")
    public ResponseEntity<SupplierProductsDTO> getSupplierWithProducts(
            @PathVariable Integer supplierId,
            @RequestParam(required = false) Integer categoryId) { // categoryId를 추가
        SupplierDTO supplier = supplierService.getSupplierDetails(supplierId, categoryId);
        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }

        SupplierProductsDTO response = new SupplierProductsDTO();
        response.setSupplier(supplier);
        response.setProducts(supplier.getProducts()); // 품목 목록 설정

        return ResponseEntity.ok(response);
    }

}
