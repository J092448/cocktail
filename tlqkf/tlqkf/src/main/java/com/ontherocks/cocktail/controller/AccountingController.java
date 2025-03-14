package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.model.Accounting;
import com.ontherocks.cocktail.model.AccountingDataDTO;
import com.ontherocks.cocktail.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:80")  // ✅ CORS 문제 해결
@RequestMapping("/api/accounting")
public class AccountingController {

    private final AccountingService accountingService;
    @Autowired
    public AccountingController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }


    // 현재 회계 데이터 가져오기
    @GetMapping("/current")
    public ResponseEntity<AccountingDataDTO> getCurrentAccountingData() {
            return ResponseEntity.ok(accountingService.getCurrentAccountingData());
        }

    // ✅ 전월 회계 데이터 가져오기
    @GetMapping("/previous")
    public ResponseEntity<AccountingDataDTO> getPreviousAccountingData() {
        return ResponseEntity.ok(accountingService.getPreviousAccountingData());
    }

    // ✅ 상품 매출원가(자동) 가져오기
    @GetMapping("/product-cost-auto")
    public ResponseEntity<?> getProductCostAuto() {
        return ResponseEntity.ok(accountingService.getProductCostAuto());
    }

    // 데이터 저장 또는 업데이트
    @PostMapping("/save")
    public ResponseEntity<?> saveAccountingData(@RequestBody AccountingDataDTO accountingDataDTO) {
        try {
            accountingService.saveOrUpdateAccountingData(accountingDataDTO);
            return ResponseEntity.ok("Data saved/updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving/updating data: " + e.getMessage());
        }
    }

    // ✅ 특정 연도와 월의 회계 데이터 조회
    @GetMapping
    public ResponseEntity<?> getAccountingData(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {

        // 연도와 월을 null 처리 후 Service로 전달
        if (year == null || month == null) {
            LocalDate currentDate = LocalDate.now();
            year = (year == null) ? currentDate.getYear() : year;
            month = (month == null) ? currentDate.getMonthValue() : month;
        }

        try {
            List<Accounting> data = accountingService.getAccountingData(year, month);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류 발생: " + e.getMessage());
        }
    }
}
