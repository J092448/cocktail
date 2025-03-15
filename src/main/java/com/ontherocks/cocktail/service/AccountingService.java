package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.model.Accounting;
import com.ontherocks.cocktail.model.AccountingDataDTO;
import com.ontherocks.cocktail.repository.AccountingMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountingService {
    private static final Logger logger = Logger.getLogger(AccountingService.class.getName());
    private final AccountingMapper accountingMapper;

    @Autowired
    public AccountingService(AccountingMapper accountingMapper) {
        this.accountingMapper = accountingMapper;
    }

    private AccountingDataDTO getDefaultAccountingData() {
        AccountingDataDTO defaultData = new AccountingDataDTO();
        defaultData.setDate(LocalDate.now());
        defaultData.setUserId(11);
        defaultData.setSales(BigDecimal.ZERO);
        defaultData.setCostOfSales(BigDecimal.ZERO);
        defaultData.setProductCostAuto(BigDecimal.ZERO);
        defaultData.setProductCostManual(BigDecimal.ZERO);
        defaultData.setBeginningInventory(BigDecimal.ZERO);
        defaultData.setEndingInventory(BigDecimal.ZERO);
        defaultData.setGrossProfit(BigDecimal.ZERO);
        defaultData.setSgAndA(BigDecimal.ZERO);
        defaultData.setSalary(BigDecimal.ZERO);
        defaultData.setTransportationCost(BigDecimal.ZERO);
        defaultData.setOfficeSuppliesCost(BigDecimal.ZERO);
        defaultData.setRentExpense(BigDecimal.ZERO);
        defaultData.setOperatingIncome(BigDecimal.ZERO);
        defaultData.setNonOperatingIncome(BigDecimal.ZERO);
        defaultData.setNonOperatingExpense(BigDecimal.ZERO);
        defaultData.setPreTaxIncome(BigDecimal.ZERO);
        defaultData.setTaxExpense(BigDecimal.ZERO);
        defaultData.setNetIncome(BigDecimal.ZERO);
        defaultData.setMonth(LocalDate.now().getMonthValue());
        defaultData.setYear(LocalDate.now().getYear());
        defaultData.setProductSales(BigDecimal.ZERO);
        return defaultData;
    }

    public AccountingDataDTO getCurrentAccountingData() {
        try {
            // DB 접근 시 로그 추가
            System.out.println("DB 연결을 시도합니다...");
            AccountingDataDTO data = accountingMapper.getCurrentAccountingData();
            return Optional.ofNullable(data).orElseGet(this::getDefaultAccountingData);
        } catch (Exception e) {
            // DataAccessException을 처리하는 코드
            System.err.println("데이터베이스 접근 오류: " + e.getMessage());
            return getDefaultAccountingData();
        }
    }

    public AccountingDataDTO getPreviousAccountingData() {
        try {
            int previousYear = LocalDate.now().getYear();
            int previousMonth = LocalDate.now().getMonthValue() - 1;
            if (previousMonth == 0) {
                previousMonth = 12;
                previousYear--;
            }
            AccountingDataDTO data = accountingMapper.getPreviousAccountingData(previousYear, previousMonth);
            if (data == null) {
                throw new DataAccessException("No previous accounting data found.") {};
            }
            return data;
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing previous accounting data.", e) {};
        }
    }
    // 데이터 저장 또는 업데이트
    @Transactional
    public void saveOrUpdateAccountingData(AccountingDataDTO accountingDataDTO) throws SQLException {
        // 필요한 필드 설정
        if (accountingDataDTO.getProductSales() == null) {
            accountingDataDTO.setProductSales(BigDecimal.ZERO); // 기본값으로 0 설정
        }
        if (accountingDataDTO.getGrossProfit() == null) {
            accountingDataDTO.setGrossProfit(BigDecimal.ZERO); // 기본값 설정
        }
        if (accountingDataDTO.getSgAndA() == null) {
            accountingDataDTO.setSgAndA(BigDecimal.ZERO); // 기본값 설정
        }
        // 쿼리 파라미터 출력
        System.out.println("쿼리 파라미터: " + accountingDataDTO);

        try {
            // 데이터베이스 저장 또는 업데이트
            accountingMapper.saveOrUpdateAccountingData(accountingDataDTO);
            System.out.println("✅ 데이터 저장 성공");
        } catch (DataAccessException e) {
            throw new RuntimeException("❌ 데이터베이스 오류: " + e.getMessage(), e);
        }
    }


    // ✅ 특정 연도와 월의 회계 데이터 조회
    public List<Accounting> getAccountingData(int year, int month) {
        return accountingMapper.getAccountingData(year, month);
    }

    public BigDecimal getProductCostAuto() {
        BigDecimal cost = accountingMapper.getProductCostAuto();
        return cost != null ? cost : BigDecimal.ZERO;
    }
}
