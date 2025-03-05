package com.ontherocks.tlqkf.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class OrderingFrmService {

    public List<String> getCategories() {
        return Arrays.asList("주류", "식자재", "소모품");
    }

    public List<String> getSubcategories(String category) {
        Map<String, List<String>> subcategoryMap = Map.of(
                "주류", Arrays.asList("와인", "맥주", "위스키"),
                "식자재", Arrays.asList("야채", "과일", "고기"),
                "소모품", Arrays.asList("냅킨", "컵", "빨대")
        );
        return subcategoryMap.getOrDefault(category, List.of());
    }

    public List<String> getItemNames(String subcategory) {
        Map<String, List<String>> itemMap = Map.of(
                "와인", Arrays.asList("레드와인", "화이트와인"),
                "맥주", Arrays.asList("IPA", "라거"),
                "위스키", Arrays.asList("싱글몰트", "버번")
        );
        return itemMap.getOrDefault(subcategory, List.of());
    }

    public String getUnitPrice(String itemName) {
        Map<String, String> priceMap = Map.of(
                "레드와인", "50000",
                "화이트와인", "40000",
                "IPA", "3000",
                "라거", "2500"
        );
        return priceMap.getOrDefault(itemName, "0");
    }
}
