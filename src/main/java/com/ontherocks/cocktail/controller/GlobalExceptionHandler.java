package com.ontherocks.cocktail.controller;

// 필요한 라이브러리 import

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

// 전역 예외 처리를 담당하는 클래스임을 선언
@ControllerAdvice
public class GlobalExceptionHandler {

    // 특정 예외(NoHandlerFoundException)를 처리하는 메서드
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 응답 상태를 404 NOT FOUND로 설정
    public String handleNotFound() {
        // 404 에러 발생 시 반환할 템플릿 경로를 지정
        return "error/404"; // 📌 `src/main/resources/templates/error/404.html` 파일이 필요
    }
}
