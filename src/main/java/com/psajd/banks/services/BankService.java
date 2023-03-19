package com.psajd.banks.services;

import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.dao.AccountDao;
import com.psajd.banks.dao.BankDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankService {

    private CentralBank centralBank;
    private BankDao bankDao;

    @Autowired
    public BankService(BankDao bankDao, CentralBank centralBank) {
        this.bankDao = bankDao;
        this.centralBank = centralBank;
    }
}
