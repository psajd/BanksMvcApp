package com.psajd.banks.services;

import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountService {

    private CentralBank centralBank;
    private AccountDao accountDao;

    @Autowired
    public AccountService(AccountDao accountDao, CentralBank centralBank) {
        this.accountDao = accountDao;
        this.centralBank = centralBank;
    }
}
