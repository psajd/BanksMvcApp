package com.psajd.banks.core.transactions;

import java.util.UUID;

import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.exceptions.TransactionException;
import lombok.Getter;
import lombok.ToString;
/**
 * implementation of transfer transaction
 */
@Getter
@ToString
public class TransferTransaction extends Transaction {

    private final Account src;
    private final Account dest;

    public TransferTransaction(Account src, Account dest, double money, UUID id) {
        super(id, money);
        this.dest = dest;
        this.src = src;
    }
    /**
     * do transfer transaction operations
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
        dest.replenishmentOperation(money);
        status = TransactionStatus.ON_DONE;
        src.getTransactions().add(this);
        return this;
    }
    /**
     * undo transfer transaction operations
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
        dest.withdrawOperation(money);
        status = TransactionStatus.ON_CANCEL;
        return this;
    }
}
