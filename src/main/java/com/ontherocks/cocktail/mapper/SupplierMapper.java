package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.SupplierDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplierMapper {

    List<SupplierDTO> findAll();

    SupplierDTO findById(Integer id);
    SupplierDTO getSupplierDetails(Integer supplierId);

    void save(SupplierDTO supplierDTO);
    void update(SupplierDTO supplierDTO); // 업데이트 메소드
    void deleteById(Integer id); // 삭제 메소드
}
