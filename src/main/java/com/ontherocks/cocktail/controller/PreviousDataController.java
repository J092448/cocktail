package com.ontherocks.cocktail.controller;

// 필요한 Spring 애노테이션 및 클래스 import

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

// REST API의 컨트롤러 클래스 선언
@RestController
// 기본 URL 경로를 "/previousData"로 설정. 이 컨트롤러의 모든 엔드포인트는 해당 경로를 기반으로 함.
@RequestMapping("/previousData")
public class PreviousDataController {

    // GET 요청을 처리하는 메서드 선언
    @GetMapping
    public Map<String, Object> getPreviousData() {
        // 응답 데이터를 저장하기 위한 Map 객체 생성
        Map<String, Object> response = new HashMap<>();

        // 응답 메시지를 Map에 추가. "message"라는 키에 메시지 값 저장.
        response.put("message", "✅ 이전 월 데이터 응답 성공");

        // 생성된 Map 객체를 반환하여 JSON 형식으로 클라이언트에 응답
        return response;
    }
}
