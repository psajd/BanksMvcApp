package com.psajd.banks.core.accounts;

/**
 * Account types:
 * CREDIT - "credit",
 * DEPOSIT - "deposit",
 * DEBIT - "debit",
 */
public enum AccountType {
    CREDIT("credit"), DEBIT("debit"), DEPOSIT("deposit");
    private final String val;

    AccountType(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "AccountType{" + "val='" + val + '\'' + '}';
    }
}
