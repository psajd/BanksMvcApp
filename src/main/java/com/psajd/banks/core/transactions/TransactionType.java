package com.psajd.banks.core.transactions;

public enum TransactionType {
    REPLENISHMENT("replenishment"),
    TRANSFER("transfer"),
    WITHDRAW("withdraw");

    private final String val;

    TransactionType(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
