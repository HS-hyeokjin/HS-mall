package com.hsmall.constant;

public enum ProductCategory {
    outer("outer"),
    top("top"),
    pants("pants"),
    shoes("shoes"),
    accessories("accessories");

    private final String value;

    ProductCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // 문자열 값을 해당하는 열거형 상수로 변환하는 정적 메서드 추가
    public static ProductCategory fromValue(String value) {
        for (ProductCategory category : ProductCategory.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        return null; // 필요에 따라 예외를 던지거나 기본값을 반환할 수 있습니다.
    }
}

