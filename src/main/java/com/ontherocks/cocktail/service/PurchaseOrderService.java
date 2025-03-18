package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.PurchaseRequestDTO;
import com.ontherocks.cocktail.dto.SupplierDTO;
import com.ontherocks.cocktail.mapper.PurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private SupplierService supplierService; // SupplierService 주입

    public PurchaseRequestDTO getOrderDetailById(Integer requestId) {
        PurchaseRequestDTO orderDetail = purchaseOrderMapper.getOrderDetailById(requestId);

        // SupplierDTO 설정
        if (orderDetail.getSupplierId() != null) {
            // supplierId로 SupplierDTO를 로드
            SupplierDTO supplier = supplierService.getSupplierById(orderDetail.getSupplierId());
            if (supplier != null) {
                orderDetail.setSupplier(supplier);
            } else {
                orderDetail.setSupplier(new SupplierDTO()); // supplier가 null인 경우 빈 객체 생성
            }
        } else {
            orderDetail.setSupplier(new SupplierDTO()); // supplierId가 없으면 빈 객체 생성
        }

        return orderDetail;
    }
}
