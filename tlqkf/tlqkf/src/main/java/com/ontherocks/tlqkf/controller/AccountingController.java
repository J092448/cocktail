package com.ontherocks.tlqkf.controller;

import com.ontherocks.tlqkf.model.AccountingDataDTO;
import com.ontherocks.tlqkf.service.AccountingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounting")
public class AccountingController {

    private final AccountingService accountingService;

    public AccountingController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    // 당월 회계 데이터 조회
    @GetMapping("/current")
    public AccountingDataDTO getCurrentAccountingData() {
        return accountingService.getCurrentAccountingData();
    }

    // 데이터 저장
    @PostMapping("/save")
    public String saveAccountingData(@RequestBody AccountingDataDTO accountingData) {
        accountingService.saveAccountingData(accountingData);
        return "저장 완료!";
    }
}
