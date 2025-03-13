package com.ontherocks.tlqkf.controller;

import org.springframework.ui.Model; // 여기에 올바른 Model 클래스를 임포트해야 합니다.
import com.ontherocks.tlqkf.model.AccountingDataDTO;
import com.ontherocks.tlqkf.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class CalculatePageController {


    private final AccountingService accountingService;

    @Autowired
    public CalculatePageController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }
    // `/calculate` 페이지로 회계 데이터를 가져와서 템플릿에 전달
    @GetMapping("/calculate")
    public String showCalculatePage(Model model) {
        try {
            AccountingDataDTO currentData = accountingService.getCurrentAccountingData();
            model.addAttribute("currentData", currentData);  // 데이터를 템플릿에 전달
            return "calculate";  // calculate.html을 렌더링
        } catch (Exception e) {
            model.addAttribute("error", "Error loading data: " + e.getMessage());
            return "error";  // 에러 페이지로 이동
        }
    }
}