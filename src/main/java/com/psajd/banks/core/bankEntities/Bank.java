package com.psajd.banks.core.bankEntities;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.psajd.banks.core.accounts.*;
import com.psajd.banks.core.clients.Client;
import com.psajd.banks.core.configuration.BankConfig;
import com.psajd.banks.core.exceptions.BankException;
import com.psajd.banks.core.notifications.IObserver;
import com.psajd.banks.core.notifications.Notification;
import com.psajd.banks.core.notifications.Observable;
import com.psajd.banks.core.time.TimeManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Bank implements Observable<Notification> {

    private final Map<Client, List<Account>> accountsByClient = new HashMap<>();
    private final List<IObserver<Notification>> _subscribers = new ArrayList<>();
    @Getter
    private TimeManager timeManager;
    @Getter
    private BankConfig bankConfig;
    @Getter
    private UUID id;
    @Getter
    private String bankName;


    public List<Client> getClients() {
        return accountsByClient.keySet().stream().toList();
    }

    public List<Account> getAccounts() {
        return accountsByClient.values().stream().flatMap(Collection::stream).toList();
    }

    /**
     * add subscriber to bank updates
     *
     * @param o subscriber
     */
    @Override
    public void addObserver(IObserver<Notification> o) {
        if (!_subscribers.contains(o)) {
            _subscribers.add(o);
        }
    }

    /**
     * remove subscriber to bank updates
     *
     * @param o subscriber
     */
    @Override
    public void removeObserver(IObserver<Notification> o) {
        _subscribers.remove(o);
    }

    /**
     * find client by account
     *
     * @param account account
     * @return returns client by account
     */
    Client findClient(Account account) {
        var optional = accountsByClient.entrySet().stream()
            .filter(x -> x.getValue().contains(account))
            .findFirst().orElse(null);
        if (optional == null) {
            return null;
        }
        return optional.getKey();
    }

    /**
     * add client to bank
     *
     * @param client new client
     */
    void addClient(Client client) {
        if (accountsByClient.containsKey(client)) {
            throw new BankException("client already exist");
        }

        accountsByClient.put(client, new ArrayList<Account>());
        addObserver(client);
    }

    /**
     * add new account to client
     *
     * @param accountType type of new account
     * @param client      new client
     */
    Account createAccount(Client client, AccountType accountType) {
        Account account;
        switch (accountType) {
            case DEBIT -> account = new DebitAccount(UUID.randomUUID(), timeManager.getNow(),
                bankConfig.getExpirationDate(), 0,
                bankConfig.getDebitRate()
            );
            case CREDIT -> account = new CreditAccount(UUID.randomUUID(), timeManager.getNow(),
                bankConfig.getExpirationDate(), 0,
                bankConfig.getCreditRate()
            );
            case DEPOSIT -> account = new DepositAccount(UUID.randomUUID(), timeManager.getNow(),
                bankConfig.getExpirationDate(), 0,
                bankConfig.getDepositRate()
            );
            default -> throw new BankException("wrong account type");
        }

        if (!accountsByClient.containsKey(client)) {
            throw new BankException();
        }

        accountsByClient.get(client).add(account);
        timeManager.addObserver(account);
        return account;
    }

    /**
     * update bank config for bank and all accounts
     *
     * @param bankConfig new bank config
     */
    void updateBankConfig(BankConfig bankConfig) {
        this.bankConfig = bankConfig;
        for (Account account : getAccounts()) {
            account.updatePercentage(bankConfig);
        }

        notifyObservers();
    }

    /**
     * notify all observers
     */
    private void notifyObservers() {
        _subscribers.forEach(x -> x.update(new Notification(new Date(), bankConfig.toString())));
    }
}
