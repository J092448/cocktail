package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.PurchaseRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseRequestDTO getOrderDetailById(@Param("requestId") Integer requestId);
}
