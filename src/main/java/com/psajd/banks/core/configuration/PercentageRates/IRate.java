package com.psajd.banks.core.configuration.PercentageRates;

/**
 * bank condition for certain type of account
 */
public interface IRate {

    /**
     * counts interest by money amount
     *
     * @param money money for counting
     * @return counted interest
     */
    double countInterest(double money);

    /**
     * counts commission by money amount
     *
     * @param money money for counting
     * @return counted commission
     */
    double countCommission(double money);

    /**
     * checks if is money in acceptable boundaries
     *
     * @param money money for checking
     * @return true if in borders, otherwise false
     */
    boolean isInBorders(double money);
}
