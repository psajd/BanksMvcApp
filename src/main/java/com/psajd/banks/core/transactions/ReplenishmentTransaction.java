package com.psajd.banks.core.transactions;

import java.util.UUID;

import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.exceptions.TransactionException;
import lombok.Getter;
import lombok.ToString;

/**
 * implementation of replenishment transaction
 */
@ToString
public class ReplenishmentTransaction extends Transaction {

    @Getter
    private final Account src;
    @Getter
    private final TransactionType type = TransactionType.REPLENISHMENT;

    public ReplenishmentTransaction(Account src, double money, UUID id) {
        super(id, money);
        this.src = src;
    }

    /**
     * do replenishment transaction operations
     *
     * @return returns this
     */
    @Override
    public Transaction doTransaction() {
        if (status != TransactionStatus.ON_WORK) {
            status = TransactionStatus.ON_ERROR;
            throw new TransactionException("on process");
        }

        src.replenishmentOperation(money);
        status = TransactionStatus.ON_DONE;
        src.getTransactions().add(this);
        return this;
    }

    /**
     * undo replenishment transaction operations
     *
     * @return returns this
     */
    @Override
    public Transaction undo() {
        if (status != TransactionStatus.ON_DONE) {
            status = TransactionStatus.ON_ERROR;
            throw new TransactionException("on process");
        }

        src.withdrawOperation(money);
        status = TransactionStatus.ON_CANCEL;
        return this;
    }
}
