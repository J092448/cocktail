package com.ontherocks.cocktail.service;

// 필요한 클래스 및 어노테이션 import

import com.ontherocks.cocktail.model.Accounting;
import com.ontherocks.cocktail.model.AccountingDataDTO;
import com.ontherocks.cocktail.repository.AccountingMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

// Service 클래스임을 명시. Spring에서 이 클래스는 비즈니스 로직을 처리하는 역할을 함.
@Service
public class AccountingService {

    // 로깅을 위한 Logger 객체 생성 (정보성 로그 출력)
    private static final Logger logger = Logger.getLogger(AccountingService.class.getName());

    // 의존성 주입: AccountingMapper를 통해 데이터베이스 접근
    private final AccountingMapper accountingMapper;

    // 생성자 주입 방식을 사용하여 AccountingMapper 초기화
    @Autowired
    public AccountingService(AccountingMapper accountingMapper) {
        this.accountingMapper = accountingMapper;
    }

    // 기본 회계 데이터를 생성하는 메서드
    private AccountingDataDTO getDefaultAccountingData() {
        AccountingDataDTO defaultData = new AccountingDataDTO();
        defaultData.setDate(LocalDate.now()); // 현재 날짜 설정
        defaultData.setUserId(11); // 기본 사용자 ID 설정
        defaultData.setSales(BigDecimal.ZERO); // 매출 기본값 설정
        defaultData.setCostOfSales(BigDecimal.ZERO); // 매출원가 기본값 설정
        defaultData.setProductCostAuto(BigDecimal.ZERO); // 자동 계산된 상품 원가 기본값 설정
        defaultData.setProductCostManual(BigDecimal.ZERO); // 수동 입력된 상품 원가 기본값 설정
        defaultData.setBeginningInventory(BigDecimal.ZERO); // 기초 재고 기본값 설정
        defaultData.setEndingInventory(BigDecimal.ZERO); // 기말 재고 기본값 설정
        defaultData.setGrossProfit(BigDecimal.ZERO); // 매출총이익 기본값 설정
        defaultData.setSgAndA(BigDecimal.ZERO); // 판매관리비 기본값 설정
        defaultData.setSalary(BigDecimal.ZERO); // 급여 기본값 설정
        defaultData.setTransportationCost(BigDecimal.ZERO); // 운송비 기본값 설정
        defaultData.setOfficeSuppliesCost(BigDecimal.ZERO); // 사무용품비 기본값 설정
        defaultData.setRentExpense(BigDecimal.ZERO); // 임대료 기본값 설정
        defaultData.setOperatingIncome(BigDecimal.ZERO); // 영업이익 기본값 설정
        defaultData.setNonOperatingIncome(BigDecimal.ZERO); // 영업외수익 기본값 설정
        defaultData.setNonOperatingExpense(BigDecimal.ZERO); // 영업외비용 기본값 설정
        defaultData.setPreTaxIncome(BigDecimal.ZERO); // 세전이익 기본값 설정
        defaultData.setTaxExpense(BigDecimal.ZERO); // 세금 비용 기본값 설정
        defaultData.setNetIncome(BigDecimal.ZERO); // 당기순이익 기본값 설정
        defaultData.setMonth(LocalDate.now().getMonthValue()); // 현재 월 설정
        defaultData.setYear(LocalDate.now().getYear()); // 현재 연도 설정
        defaultData.setProductSales(BigDecimal.ZERO); // 상품 매출 기본값 설정
        return defaultData;
    }

    // 현재 회계 데이터를 가져오는 메서드
    public AccountingDataDTO getCurrentAccountingData() {
        try {
            // 데이터베이스 접근 로그 출력
            System.out.println("DB 연결을 시도합니다...");
            // 현재 회계 데이터 조회
            AccountingDataDTO data = accountingMapper.getCurrentAccountingData();
            // 데이터가 null일 경우 기본 데이터를 반환
            return Optional.ofNullable(data).orElseGet(this::getDefaultAccountingData);
        } catch (Exception e) {
            // 데이터베이스 예외 처리
            System.err.println("데이터베이스 접근 오류: " + e.getMessage());
            return getDefaultAccountingData();
        }
    }

    // 이전 회계 데이터를 가져오는 메서드
    public AccountingDataDTO getPreviousAccountingData() {
        try {
            int previousYear = LocalDate.now().getYear(); // 현재 연도
            int previousMonth = LocalDate.now().getMonthValue() - 1; // 이전 월 계산
            if (previousMonth == 0) { // 월이 0일 경우 연도를 감소시키고 12월로 설정
                previousMonth = 12;
                previousYear--;
            }
            // 이전 월의 회계 데이터 조회
            AccountingDataDTO data = accountingMapper.getPreviousAccountingData(previousYear, previousMonth);
            if (data == null) {
                // 데이터가 없을 경우 예외 처리
                throw new DataAccessException("No previous accounting data found.") {};
            }
            return data;
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            throw new DataAccessException("Error accessing previous accounting data.", e) {};
        }
    }

    // 회계 데이터를 저장하거나 업데이트하는 메서드
    @Transactional
    public void saveOrUpdateAccountingData(AccountingDataDTO accountingDataDTO) throws SQLException {
        // 필수 필드의 기본값 설정
        if (accountingDataDTO.getProductSales() == null) {
            accountingDataDTO.setProductSales(BigDecimal.ZERO);
        }
        if (accountingDataDTO.getGrossProfit() == null) {
            accountingDataDTO.setGrossProfit(BigDecimal.ZERO);
        }
        if (accountingDataDTO.getSgAndA() == null) {
            accountingDataDTO.setSgAndA(BigDecimal.ZERO);
        }
        // 쿼리 파라미터 디버깅 출력
        System.out.println("쿼리 파라미터: " + accountingDataDTO);

        try {
            // 데이터베이스 저장 또는 업데이트 수행
            accountingMapper.saveOrUpdateAccountingData(accountingDataDTO);
            System.out.println("✅ 데이터 저장 성공");
        } catch (DataAccessException e) {
            // 데이터베이스 접근 중 오류가 발생한 경우 예외 처리
            throw new RuntimeException("❌ 데이터베이스 오류: " + e.getMessage(), e);
        }
    }

    // ✅ 특정 연도와 월의 회계 데이터를 조회하는 메서드
    public List<Accounting> getAccountingData(int year, int month) {
        // 매퍼를 호출하여 특정 연도와 월의 데이터를 가져옴
        return accountingMapper.getAccountingData(year, month);
    }

    // 상품 매출원가(자동 계산)를 가져오는 메서드
    public BigDecimal getProductCostAuto() {
        // 데이터베이스에서 상품 매출원가를 조회
        BigDecimal cost = accountingMapper.getProductCostAuto();
        // cost가 null일 경우 기본값(BigDecimal.ZERO)을 반환
        return cost != null ? cost : BigDecimal.ZERO;
    }
}
