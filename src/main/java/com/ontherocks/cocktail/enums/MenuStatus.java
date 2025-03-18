package com.ontherocks.cocktail.enums;  // ✅ 패키지 선언
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuStatus {

    판매중("판매중"),  // ✅ 메뉴가 판매 중인 상태
    품절("품절");    // ✅ 메뉴가 품절된 상태

    private final String value;  // ✅ 상태 값을 저장할 필드 (Enum의 문자열 값)

    // ✅ 생성자
    MenuStatus(String value) {
        this.value = value;
    }

    // ✅ JSON 변환 시 문자열 값으로 반환
    @JsonValue
    public String getValue() {
        return value;
    }

    // ✅ 문자열을 Enum으로 변환하는 메서드 (대소문자 구분 없음)
    @JsonCreator
    public static MenuStatus fromString(String status) {
        for (MenuStatus ms : MenuStatus.values()) {
            if (ms.getValue().equalsIgnoreCase(status)) {  // ✅ 대소문자 구분 없이 비교
                return ms;
            }
        }
        throw new IllegalArgumentException("❌ Unknown status: " + status);
        // ✅ 유효하지 않은 문자열 값이 들어오면 예외 발생
    }
}
