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
        AccountingDataDTO data = accountingMapper.getCurrentAccountingData();
        if (data == null) {
            data = new AccountingDataDTO();
        }
        return data;
    }

    public AccountingDataDTO getPreviousAccountingData() {
        LocalDate currentDate = LocalDate.now();
        int previousYear = currentDate.getYear();
        int previousMonth = currentDate.getMonthValue() - 1;

        if (previousMonth == 0) {
            previousMonth = 12;
            previousYear--;
        }
        AccountingDataDTO data = accountingMapper.getPreviousAccountingData(previousYear, previousMonth);
        return (data != null) ? data : new AccountingDataDTO();
    }
    // 데이터 저장 또는 업데이트
    public void saveOrUpdateAccountingData(AccountingDataDTO accountingDataDTO) throws SQLException {
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
