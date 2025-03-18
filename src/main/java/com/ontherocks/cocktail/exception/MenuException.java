package com.ontherocks.cocktail.exception;  // ✅ 예외 처리를 위한 패키지 선언

/**
 * ✅ 메뉴 관련 예외를 처리하기 위한 사용자 정의 예외 클래스
 * - `RuntimeException`을 상속하여 런타임 예외로 동작함
 * - 메뉴 조회, 등록, 수정, 삭제 과정에서 발생하는 예외를 한 곳에서 관리할 수 있도록 설계됨
 */
public class MenuException extends RuntimeException {

    /**
     * ✅ 기본 생성자
     * - 예외 메시지만 전달하는 생성자
     * - 예: throw new MenuException("메뉴를 찾을 수 없습니다.");
     * @param message 예외 발생 시 출력할 메시지
     */
    public MenuException(String message) {
        super(message);  // ✅ 부모 클래스(RuntimeException)의 생성자를 호출하여 메시지를 저장
    }

    /**
     * ✅ 원인(cause)을 포함한 생성자
     * - 예외 메시지와 함께 원인이 되는 예외 객체도 포함할 수 있음
     * - 예: throw new MenuException("메뉴 수정 중 오류 발생", e);
     * @param message 예외 메시지
     * @param cause 원인이 되는 예외 객체 (ex. SQLException, NullPointerException 등)
     */
    public MenuException(String message, Throwable cause) {
        super(message, cause);  // ✅ 부모 클래스(RuntimeException)의 생성자를 호출하여 메시지와 원인 저장
    }
}
