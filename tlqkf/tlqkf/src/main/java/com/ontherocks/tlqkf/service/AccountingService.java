package com.ontherocks.tlqkf.service;

import com.ontherocks.tlqkf.model.AccountingDataDTO;
import com.ontherocks.tlqkf.repository.AccountingMapper;
import org.springframework.stereotype.Service;

@Service
public class AccountingService {

    private final AccountingMapper accountingMapper;

    public AccountingService(AccountingMapper accountingMapper) {
        this.accountingMapper = accountingMapper;
    }

    public AccountingDataDTO getCurrentAccountingData() {
        return accountingMapper.getCurrentAccountingData();
    }

    public void saveAccountingData(AccountingDataDTO accountingData) {
        accountingMapper.saveAccountingData(accountingData);
    }
}
