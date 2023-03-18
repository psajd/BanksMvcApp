package com.psajd.banks.core.transactions;

/**
 * Status in command pattern. Transaction status.
 */
public enum TransactionStatus {
    ON_WORK("onWork"), ON_ERROR("onError"), ON_DONE("onDone"), ON_CANCEL("onCancel");

    private final String val;

    TransactionStatus(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "TransactionStatus{" + "val='" + val + '\'' + '}';
    }
}
