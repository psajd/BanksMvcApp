package com.psajd.banks.core.configuration.PercentageRates;

import com.psajd.banks.core.exceptions.PercentageRateException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * bank condition for credit type of account
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreditRate implements IRate {

    private double lowerBorder = -10000;
    private double upperBorder = 1000000;
    private double commission = 10;

    /**
     * counts interest by money amount
     *
     * @param money money for counting
     * @return counted interest
     */
    @Override
    public double countInterest(double money) {
        return 0;
    }

    /**
     * counts commission by money amount
     *
     * @param money money for counting
     * @return counted commission
     */
    @Override
    public double countCommission(double money) {
        if (money < lowerBorder || money > upperBorder) {
            throw new PercentageRateException("money limit");
        }

        return money > 0 ? 0 : -getCommission();
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
