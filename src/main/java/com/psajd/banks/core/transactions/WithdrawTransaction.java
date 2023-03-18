package com.psajd.banks.core.transactions;

import java.util.UUID;

import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.exceptions.TransactionException;
import lombok.Getter;

/**
 * implementation of withdraw transaction
 */
public class WithdrawTransaction extends Transaction {

    @Getter
    private final Account src;

    public WithdrawTransaction(Account src, double money, UUID id) {
        super(id, money);
        this.src = src;
    }
    /**
     * do withdraw transaction operations
     *
     * @return returns this
     */
    @Override
    public Transaction doTransaction() {
        if (status != TransactionStatus.ON_WORK) {
            status = TransactionStatus.ON_ERROR;
            throw new TransactionException("on process");
        }

        src.withdrawOperation(money);
        status = TransactionStatus.ON_DONE;
        src.getTransactions().add(this);
        return this;
    }
    /**
     * undo withdraw transaction operations
     *
     * @return returns this
     */
    @Override
    public Transaction undo() {
        if (status != TransactionStatus.ON_DONE) {
            status = TransactionStatus.ON_ERROR;
            throw new TransactionException("on process");
        }

        src.replenishmentOperation(money);
        status = TransactionStatus.ON_CANCEL;
        return this;
    }
}
