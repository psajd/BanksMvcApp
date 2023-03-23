package com.psajd.banks.core.accounts;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.psajd.banks.core.configuration.BankConfig;
import com.psajd.banks.core.configuration.PercentageRates.IRate;
import com.psajd.banks.core.notifications.IObserver;
import com.psajd.banks.core.time.TimeManager;
import com.psajd.banks.core.transactions.Transaction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Abstract account class
 */
@EqualsAndHashCode
@Getter
@Setter
@ToString
public abstract class Account implements IObserver<TimeManager> {

    protected UUID id;
    protected LocalDate foundationDate;
    protected LocalDate lastUpdate;
    protected LocalDate expirationDate;

    protected AccountType accountType;
    protected double moneyAmount;

    private final List<Transaction> transactions = new ArrayList<>();

    protected IRate rate;

    public Account(UUID id, LocalDate foundationDate,
                   LocalDate expirationDate, double moneyAmount, IRate rate) {
        this.id = id;
        this.foundationDate = foundationDate;
        this.lastUpdate = foundationDate;
        this.expirationDate = expirationDate;
        this.moneyAmount = moneyAmount;
        this.rate = rate;
    }

    /**
     * calculating of monthly payments
     *
     * @param value current time
     */
    private void calculateMonthlyInterests(LocalDate value) {
        lastUpdate = lastUpdate.plusMonths(1);
        while (lastUpdate.isBefore(value)) {
            lastUpdate = lastUpdate.plusMonths(1);
            moneyAmount += rate.countInterest(moneyAmount);
        }
    }

    /**
     * IObserver method override
     *
     * @param timeManager time manager, give access of current time
     */
    @Override
    public void update(TimeManager timeManager) {
        calculateMonthlyInterests(timeManager.getNow());
    }

    /**
     * update bank config
     *
     * @param percentageRate bank config
     */
    public abstract void updatePercentage(BankConfig percentageRate);

    /**
     * do withdraw money from account
     *
     * @param money money for operation
     */
    abstract public void withdrawOperation(double money);

    /**
     * do replenishment money from account
     *
     * @param money money for operation
     */
    abstract public void replenishmentOperation(double money);

    /**
     * do transaction money from account to another account
     *
     * @param money   money for operation
     * @param account destination account
     */
    abstract public void transferOperation(double money, Account account);

}
