package com.ontherocks.cocktail.controller;

// 모델, 서비스, HTTP 응답, 애노테이션 등을 위한 필요한 import

import com.ontherocks.cocktail.model.Accounting;
import com.ontherocks.cocktail.model.AccountingDataDTO;
import com.ontherocks.cocktail.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// 이 클래스가 REST 컨트롤러임을 나타냄. HTTP 요청을 처리하고 RESTful 응답을 생성.
@RestController
// 다른 출처(CORS)에서 오는 요청을 허용. 여기선 http://localhost:80에서 오는 요청만 허용하여 프런트엔드와 백엔드 간의 CORS 문제 해결.
@CrossOrigin(origins = "http://localhost:80")
// 컨트롤러 내의 모든 엔드포인트에 대해 기본 URI를 지정하여 구조화된 요청 처리 가능.
@RequestMapping("/api/accounting")
public class AccountingController {

    // AccountingService를 의존성으로 사용. 비즈니스 로직 처리를 위임.
    private final AccountingService accountingService;

    // 의존성 주입을 통해 AccountingService 객체 생성 및 관리.
    @Autowired
    public AccountingController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    // ✅ 현재 회계 데이터를 가져오는 엔드포인트
    @GetMapping("/current")
    public ResponseEntity<AccountingDataDTO> getCurrentAccountingData() {
        // 현재 회계 데이터를 가져와 HTTP 200 상태로 반환.
        return ResponseEntity.ok(accountingService.getCurrentAccountingData());
    }

    // ✅ 전월 회계 데이터를 가져오는 엔드포인트
    @GetMapping("/previous")
    public ResponseEntity<AccountingDataDTO> getPreviousAccountingData() {
        // 전월 회계 데이터를 가져와 응답으로 반환.
        return ResponseEntity.ok(accountingService.getPreviousAccountingData());
    }

    // ✅ 상품 매출원가(자동 계산)를 가져오는 엔드포인트
    @GetMapping("/product-cost-auto")
    public ResponseEntity<?> getProductCostAuto() {
        // 자동으로 계산된 상품 매출원가 데이터를 반환.
        return ResponseEntity.ok(accountingService.getProductCostAuto());
    }

    // ✅ 회계 데이터를 저장 또는 업데이트하는 엔드포인트
    @PostMapping("/save")
    public ResponseEntity<?> saveAccountingData(@RequestBody AccountingDataDTO accountingDataDTO) {
        try {
            // 받은 데이터를 저장하거나 업데이트 시도.
            accountingService.saveOrUpdateAccountingData(accountingDataDTO);
            return ResponseEntity.ok("데이터가 성공적으로 저장/업데이트되었습니다.");
        } catch (Exception e) {
            // 예외 발생 시, HTTP 500 상태와 에러 메시지를 반환.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("데이터 저장/업데이트 중 오류 발생: " + e.getMessage());
        }
    }

    // ✅ 특정 연도와 월의 회계 데이터를 가져오는 엔드포인트
    @GetMapping
    public ResponseEntity<?> getAccountingData(
            // 연도 및 월을 쿼리 파라미터로 받음. 필수 항목이 아니므로 null일 수 있음.
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {

        // 연도나 월이 제공되지 않을 경우, 현재 연도 및 월로 기본값 설정.
        if (year == null || month == null) {
            LocalDate currentDate = LocalDate.now();
            year = (year == null) ? currentDate.getYear() : year;
            month = (month == null) ? currentDate.getMonthValue() : month;
        }

        try {
            // 지정된 연도와 월에 대한 회계 데이터를 가져옴.
            List<Accounting> data = accountingService.getAccountingData(year, month);
            return ResponseEntity.ok(data); // 데이터를 HTTP 200 상태로 반환.
        } catch (Exception e) {
            // 예외 발생 시, HTTP 500 상태와 에러 메시지를 반환.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류 발생: " + e.getMessage());
        }
    }
}
