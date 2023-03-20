package com.psajd.banks.core.configuration.PercentageRates;

import com.psajd.banks.core.exceptions.PercentageRateException;
import lombok.*;

/**
 * bank condition for deposit type of account
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DepositRate implements IRate {

    private final double lowerBorder = 0;
    private double lowerPercentage = 1;
    private double middleBorder = 50000;
    private double middlePercentage = 2;
    private double upperBorder = 100000;
    private double upperPercentage = 3;

    /**
     * counts interest by money amount
     *
     * @param money money for counting
     * @return counted interest
     */
    @Override
    public double countInterest(double money) {
        if (money < getLowerBorder()) {
            throw new PercentageRateException("money limit");
        } else if (getLowerBorder() <= money && money < getMiddleBorder()) {
            return getLowerPercentage() * money / 100;
        } else if (getMiddlePercentage() <= money && money < getUpperBorder()) {
            return getMiddlePercentage() * money / 100;
        } else {
            return getUpperPercentage() * money / 100;
        }
    }

    /**
     * counts commission by money amount
     *
     * @param money money for counting
     * @return counted commission
     */
    @Override
    public double countCommission(double money) {
        if (money > upperBorder) {
            throw new PercentageRateException("money limit");
        }

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
        return lowerBorder < money;
    }
}
