package com.psajd.banks.core.bankEntities;


import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.accounts.AccountType;
import com.psajd.banks.core.clients.Client;
import com.psajd.banks.core.configuration.BankConfig;
import com.psajd.banks.core.time.TimeManager;
import com.psajd.banks.core.transactions.Transaction;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * bank service to manage banks
 */
@Component
public class CentralBank {

    private static CentralBank centralBank;
    @Getter
    private final List<Bank> banks = new ArrayList<>();
    @Getter
    private final TimeManager timeManager;

    @Autowired
    public CentralBank(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public Bank addBank(Bank bank) {
        banks.add(bank);
        return bank;
    }

    /**
     * add client to bank
     *
     * @param bank   destination bank
     * @param client client
     */
    public void addClientToBank(Bank bank, Client client) {
        bank.addClient(client);
    }

    /**
     * add new account to client
     *
     * @param accountType type of new account
     * @param client      new client
     * @param bank        destination bank
     * @return instance of account
     */
    public Account createAccount(Bank bank, Client client, AccountType accountType) {
        Account account = bank.createAccount(client, accountType, timeManager.getNow());
        timeManager.addObserver(account);
        return account;
    }

    /**
     * update bank info
     *
     * @param bank            destination bank
     * @param bankInformation new bank config
     */
    public void updateBankInfo(Bank bank, BankConfig bankInformation) {
        bank.updateBankConfig(bankInformation);
    }

    public List<Client> getClients() {
        return banks.stream().flatMap(x -> x.getClients().stream()).toList();
    }

    public List<Account> getAccounts() {
        return banks.stream().flatMap(x -> x.getAccounts().stream()).toList();
    }

    public List<Transaction> getTransactions() {
        return getAccounts().stream().flatMap(x -> x.getTransactions().stream()).toList();
    }

    /**
     * find bank by account
     *
     * @param account account
     * @return found bank or null
     */
    public Bank findBank(Account account) {
        return banks.stream().filter(x -> x.getAccounts().contains(account)).findFirst()
                .orElse(null);
    }

    public Bank findBank(UUID uuid) {
        return banks.stream().filter(x -> x.getId().equals(uuid)).findFirst()
                .orElse(null);
    }

    /**
     * find bank by name
     *
     * @param name client name
     * @return found bank or null
     */
    public Bank findBank(String name) {
        return banks.stream().filter(x -> x.getBankName().equals(name)).findFirst()
                .orElse(null);
    }

    /**
     * find client by id
     *
     * @param id client id
     * @return found client or null
     */
    public Client findClient(UUID id) {
        return getClients().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * find transaction by id
     *
     * @param id transaction id
     */
    public Transaction findTransaction(UUID id) {
        return getTransactions().stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElse(null);
    }

    /**
     * find account by id
     *
     * @param id account id
     * @return found account or null
     */
    public Account findAccount(UUID id) {
        return getAccounts().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * find client by account
     *
     * @param account client account
     * @return found client or null
     */
    public Client findClient(Account account) {
        Bank bank = findBank(account);
        if (bank == null) {
            return null;
        }
        return bank.findClient(account);
    }

    public void deleteBank(UUID uuid) {
        Bank bank = findBank(uuid);
        banks.remove(bank);
    }

    public Bank updateBank(Bank bank) {
        Bank oldBank = findBank(bank.getId());
        if (oldBank != null) {
            oldBank.updateBankConfig(bank.getBankConfig());
            oldBank.setBankName(bank.getBankName());
        }
        return oldBank;
    }
}
