package com.ontherocks.cocktail.controller;

// Spring의 Model 클래스를 사용해 데이터를 템플릿으로 전달

import com.ontherocks.cocktail.model.AccountingDataDTO;
import com.ontherocks.cocktail.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 이 클래스는 웹 요청을 처리하고 특정 HTML 페이지를 렌더링하는 컨트롤러 역할을 함
@Controller
public class CalculatePageController {

    // AccountingService를 의존성으로 받아옴
    private final AccountingService accountingService;

    // 생성자 기반 의존성 주입을 사용
    @Autowired
    public CalculatePageController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    // `/calculate` URI에 GET 요청이 들어오면 처리하는 메서드
    @GetMapping("/calculate")
    public String showCalculatePage(Model model) {
        try {
            // 현재 회계 데이터를 가져오기 위해 AccountingService의 메서드를 호출
            AccountingDataDTO currentData = accountingService.getCurrentAccountingData();

            // 가져온 데이터를 템플릿에 전달하기 위해 Model 객체에 추가
            model.addAttribute("currentData", currentData);

            // "calculate"라는 이름의 템플릿(calculate.html)을 반환하여 렌더링
            return "pages/calculate";
        } catch (Exception e) {
            // 데이터 로딩 중 오류가 발생하면 에러 메시지를 Model에 추가
            model.addAttribute("error", "Error loading data: " + e.getMessage());

            // 에러가 발생한 경우 "error"라는 이름의 템플릿(error.html)을 렌더링
            return "error";
        }
    }
}
