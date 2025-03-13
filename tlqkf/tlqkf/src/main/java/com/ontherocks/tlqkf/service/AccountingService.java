package com.ontherocks.tlqkf.service;

import com.ontherocks.tlqkf.model.Accounting;
import com.ontherocks.tlqkf.model.AccountingDataDTO;
import com.ontherocks.tlqkf.repository.AccountingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountingService {

    private final AccountingMapper accountingMapper;

    @Autowired
    public AccountingService(AccountingMapper accountingMapper) {
        this.accountingMapper = accountingMapper;
    }

    public AccountingDataDTO getCurrentAccountingData() {
        try {
            // DB 접근 시 로그 추가
            System.out.println("DB 연결을 시도합니다...");
            AccountingDataDTO data = accountingMapper.getCurrentAccountingData();
            if (data == null) {
                throw new DataAccessException("No current accounting data found.") {
                };
            }
            return data;
        } catch (DataAccessException e) {
            // DataAccessException을 처리하는 코드
            System.err.println("데이터베이스 접근 오류: " + e.getMessage());
            throw new DataAccessException("Error accessing current accounting data.", e) {
            };
        } catch (Exception e) {
            // 일반 예외 처리
            System.err.println("예상치 못한 오류 발생: " + e.getMessage());
            throw new DataAccessException("Unexpected error: " + e.getMessage(), e) {
            };
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
    public void saveOrUpdateAccountingData(AccountingDataDTO accountingDataDTO) throws SQLException {
        // 필요한 필드 설정
        if (accountingDataDTO.getProductSales() == null) {
            accountingDataDTO.setProductSales(BigDecimal.ZERO); // 기본값으로 0 설정
        }

        // 쿼리 파라미터 출력
        System.out.println("쿼리 파라미터: " + accountingDataDTO);

        try {
            accountingMapper.saveOrUpdateAccountingData(accountingDataDTO);
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
