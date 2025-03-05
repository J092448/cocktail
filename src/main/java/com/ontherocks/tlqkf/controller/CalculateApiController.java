package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.model.Calculate;
import com.ontherocks.tlqkf.service.CalculateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api/calculate")
public class CalculateApiController {
    private final CalculateService calculateService;

    public CalculateApiController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @GetMapping("/sales/monthly")
    public List<Calculate> getMonthlySales() {
        try {
            List<Calculate> result = calculateService.getMonthlyCalculatedData();
            if (result == null || result.isEmpty()) {
                throw new RuntimeException("데이터가 없습니다.");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("매출 데이터를 가져오는 중 오류 발생: " + e.getMessage());
        }
    }
}
