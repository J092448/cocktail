package com.ontherocks.cocktail.enums;

import java.util.Arrays;

public enum InventoryStatus {
    NORMAL("정상"),
    LOW_STOCK("발주필요"),
    OUT_OF_STOCK("품절");

    private final String value;

    InventoryStatus(String value) {
        this.value = value;
    }

    public String getValue() { return value; }

    public static InventoryStatus fromString(String text) {
        return Arrays.stream(InventoryStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(text)) // ✅ 대소문자 구분 없이 비교
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for value: " + text));
    }

    public static InventoryStatus fromStringOrDefault(String text, InventoryStatus defaultStatus) {
        return Arrays.stream(InventoryStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(text))
                .findFirst()
                .orElse(defaultStatus);
    }

    public static InventoryStatus getStatus(double quantity, double reorderLevel) {
        if (quantity <= 0) return OUT_OF_STOCK;
        if (quantity < reorderLevel) return LOW_STOCK;
        return NORMAL;
    }
}
