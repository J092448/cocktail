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

            if (salesList == null) salesList = List.of();
            if (costList == null) costList = List.of();

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
            return List.of(); // 예외 발생 시 빈 리스트 반환
        }
    }

}
