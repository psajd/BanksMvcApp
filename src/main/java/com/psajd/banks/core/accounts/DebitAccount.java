package com.psajd.banks.core.accounts;


import com.psajd.banks.core.configuration.BankConfig;
import com.psajd.banks.core.configuration.PercentageRates.IRate;
import com.psajd.banks.core.exceptions.TransactionException;

import java.time.LocalDate;
import java.util.UUID;

/**
 * implementation of Account,
 * contains debit percentage rate
 */
public class DebitAccount extends Account {

    public DebitAccount(UUID id, LocalDate foundationDate, LocalDate expirationDate,
                        double moneyAmount, IRate rate) {
        super(id, foundationDate, expirationDate, moneyAmount, rate);
        accountType = AccountType.DEBIT;
    }

    /**
     * update bank config
     *
     * @param percentageRate bank config
     */
    @Override
    public void updatePercentage(BankConfig percentageRate) {
        rate = percentageRate.getDebitRate();
    }

    /**
     * do withdraw money from account
     *
     * @param money money for operation
     */
    @Override
    public void withdrawOperation(double money) {
        if (money < 0) {
            throw new TransactionException("money < 0");
        }

        if (moneyAmount - money < 0) {
            throw new TransactionException("money amount < 0");
        }

        moneyAmount -= money - rate.countCommission(money);
    }

    /**
     * do replenishment money from account
     *
     * @param money money for operation
     */
    @Override
    public void replenishmentOperation(double money) {
        if (money < 0) {
            throw new TransactionException("money < 0");
        }

        moneyAmount += money - rate.countCommission(money);
    }

    /**
     * do transaction money from account to another account
     *
     * @param money   money for operation
     * @param account destination account
     */
    @Override
    public void transferOperation(double money, Account account) {
        if (money < 0) {
            throw new TransactionException("money < 0");
        }

        withdrawOperation(money);
        account.replenishmentOperation(money);
    }
}
