package com.psajd.banks.services;

import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.accounts.AccountType;
import com.psajd.banks.core.bankEntities.Bank;
import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.core.clients.Client;
import com.psajd.banks.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AccountService {

    private CentralBank centralBank;
    private AccountDao accountDao;

    @Autowired
    public AccountService(AccountDao accountDao, CentralBank centralBank) {
        this.accountDao = accountDao;
        this.centralBank = centralBank;
    }

    public Account addNewAccount(Bank bank, Client client, AccountType accountType) {
        Account account = centralBank.createAccount(bank, client, accountType);
        return account;
    }

    public List<Account> getAccounts(Bank bank, Client client) {
        return bank.getAccounts(client);
    }

    public Account getAccount(Bank bank, UUID uuid) {
        return bank.getAccount(uuid);
    }

    public void deleteAccount(Bank bank, Client client, UUID uuid) {
        bank.removeAccount(client, getAccount(bank, uuid));
    }

    public Account updateAccount(Bank bank, Account account) {
        Account acc = bank.updateAccount(account);

        return acc;
    }

}
