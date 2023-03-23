package com.psajd.banks.services;

import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.accounts.AccountType;
import com.psajd.banks.core.bankEntities.Bank;
import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.core.clients.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AccountService {

    private final CentralBank centralBank;

    @Autowired
    public AccountService(CentralBank centralBank) {
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

    public Account getAccount(UUID uuid) {
        return centralBank.getAccounts().stream().filter(x -> x.getId().equals(uuid)).findFirst().orElse(null);
    }
}
