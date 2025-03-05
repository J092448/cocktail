package com.ontherocks.tlqkf.service;

import com.ontherocks.tlqkf.model.Calculate;
import com.ontherocks.tlqkf.repository.CalculateMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculateService {
    private final CalculateMapper calculateMapper;

    public CalculateService(CalculateMapper calculateMapper) {
        this.calculateMapper = calculateMapper;
    }

    public List<Calculate> getMonthlyCalculatedData() {
        try {
            List<Calculate> salesList = calculateMapper.findAllSales();
            List<Calculate> costList = calculateMapper.findAllCosts();

            if (salesList == null || costList == null) {
                throw new RuntimeException("DB에서 데이터를 가져오지 못했습니다.");
            }

            Map<String, Integer> salesMap = salesList.stream()
                    .collect(Collectors.toMap(Calculate::getMonth, Calculate::getTotalSales, (a, b) -> a));

            Map<String, Integer> costMap = costList.stream()
                    .collect(Collectors.toMap(Calculate::getMonth, Calculate::getTotalCost, (a, b) -> a));

            return salesMap.keySet().stream().map(month -> {
                int totalSales = salesMap.getOrDefault(month, 0);
                int totalCost = costMap.getOrDefault(month, 0);
                return new Calculate(month, totalSales, totalCost);
            }).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("매출 데이터 계산 중 오류 발생: " + e.getMessage());
        }
    }
}
