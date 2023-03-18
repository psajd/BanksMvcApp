package com.psajd.banks.core.transactions;

import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

/**
 * Abstract transaction class, implementation of pattern "command"
 */
@Getter
@ToString
public abstract class Transaction {

    protected final UUID id;
    protected TransactionStatus status = TransactionStatus.ON_WORK;
    protected final double money;

    protected Transaction(UUID id, double money) {
        this.id = id;
        this.money = money;
    }

    /**
     * do transaction operations
     *
     * @return returns this
     */
    public abstract Transaction doTransaction();
    /**
     * undo transaction operations
     *
     * @return returns this
     */
    public abstract Transaction undo();
}
