package com.psajd.banks.core.configuration.PercentageRates;

import com.psajd.banks.core.exceptions.TransactionException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * bank condition for debit type of account
 */
@Getter
@ToString
@NoArgsConstructor
public class DebitRate implements IRate {

    private final double lowerBorder = 0;
    private double upperBorder = 100000;
    private double percentage = 1;

    public DebitRate(double upperBorder, double percentage) {
        this.upperBorder = upperBorder;
        this.percentage = percentage;
    }

    /**
     * counts interest by money amount
     *
     * @param money money for counting
     * @return counted interest
     */
    @Override
    public double countInterest(double money) {
        if (money > upperBorder) {
            throw new TransactionException("money limit");
        }

        return percentage * money / 100;
    }

    /**
     * counts commission by money amount
     *
     * @param money money for counting
     * @return counted commission
     */
    @Override
    public double countCommission(double money) {
        return 0;
    }

    /**
     * checks if is money in acceptable boundaries
     *
     * @param money money for checking
     * @return true if in borders, otherwise false
     */
    @Override
    public boolean isInBorders(double money) {
        return (lowerBorder < money) && (upperBorder > money);
    }
}
