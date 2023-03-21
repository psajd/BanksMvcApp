package com.psajd.banks.core.bankEntities;


import com.psajd.banks.core.accounts.*;
import com.psajd.banks.core.clients.Client;
import com.psajd.banks.core.configuration.BankConfig;
import com.psajd.banks.core.exceptions.BankException;
import com.psajd.banks.core.notifications.IObserver;
import com.psajd.banks.core.notifications.Notification;
import com.psajd.banks.core.notifications.Observable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@ToString
@Component
public class Bank implements Observable<Notification> {

    private final Map<Client, List<Account>> accountsByClient = new HashMap<>();
    private final List<IObserver<Notification>> subscribers = new ArrayList<>();

    private BankConfig bankConfig = new BankConfig();

    private UUID id;

    private String bankName = "bank";

    public Bank(BankConfig bankConfig, UUID id, String bankName) {
        this.bankConfig = bankConfig;
        this.id = id;
        this.bankName = bankName;
    }

    public Bank() {
    }


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
        if (!subscribers.contains(o)) {
            subscribers.add(o);
        }
    }

    /**
     * remove subscriber to bank updates
     *
     * @param o subscriber
     */
    @Override
    public void removeObserver(IObserver<Notification> o) {
        subscribers.remove(o);
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

        accountsByClient.put(client, new ArrayList<>());
        addObserver(client);
    }

    /**
     * add new account to client
     *
     * @param accountType type of new account
     * @param client      new client
     */
    Account createAccount(Client client, AccountType accountType, LocalDate now) {
        Account account;
        switch (accountType) {
            case DEBIT -> account = new DebitAccount(UUID.randomUUID(), now,
                    bankConfig.getExpirationDate(), 0,
                    bankConfig.getDebitRate()
            );
            case CREDIT -> account = new CreditAccount(UUID.randomUUID(), now,
                    bankConfig.getExpirationDate(), 0,
                    bankConfig.getCreditRate()
            );
            case DEPOSIT -> account = new DepositAccount(UUID.randomUUID(), now,
                    bankConfig.getExpirationDate(), 0,
                    bankConfig.getDepositRate()
            );
            default -> throw new BankException("wrong account type");
        }

        if (!accountsByClient.containsKey(client)) {
            throw new BankException();
        }

        accountsByClient.get(client).add(account);
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

    public Client getClient(UUID uuid) {
        return getClients().stream().filter(x -> x.getId().equals(uuid)).findFirst().orElse(null);
    }

    public void removeClient(UUID uuid) {
        Client client = getClient(uuid);
        accountsByClient.remove(client);
        subscribers.remove(client);
    }

    public Client updateClient(Client newClient) {
        Client oldClient = getClient(newClient.getId());
        oldClient.setClientName(newClient.getClientName() == null ? oldClient.getClientName() : newClient.getClientName());
        oldClient.setAddress(newClient.getAddress() == null ? oldClient.getAddress() : newClient.getAddress());
        oldClient.setPassport(newClient.getPassport() == null ? oldClient.getPassport() : newClient.getPassport());
        oldClient.setPhoneNumber(newClient.getPhoneNumber() == null ? oldClient.getPhoneNumber() : newClient.getPhoneNumber());

        return oldClient;
    }

    public List<Account> getAccounts(Client client) {
        return accountsByClient.get(client);
    }

    public Account getAccount(UUID uuid) {
        return getAccounts().stream().filter(x -> x.getId().equals(uuid)).findFirst().orElse(null);
    }

    /**
     * notify all observers
     */
    private void notifyObservers() {
        subscribers.forEach(x -> x.update(new Notification(new Date(), bankConfig.toString())));
    }

    public void removeAccount(Client client, Account account) {
        accountsByClient.get(client).remove(account);
    }

    public Account updateAccount(Account account) {
        Account oldAccount = getAccount(account.getId());
        oldAccount.setRate(account.getRate());
        oldAccount.setLastUpdate(account.getLastUpdate());
        oldAccount.setExpirationDate(account.getExpirationDate());
        oldAccount.setFoundationDate(account.getFoundationDate());
        oldAccount.setMoneyAmount(account.getMoneyAmount());
        return oldAccount;
    }
}
