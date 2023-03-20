package com.psajd.banks.core.configuration;

import com.psajd.banks.core.configuration.PercentageRates.CreditRate;
import com.psajd.banks.core.configuration.PercentageRates.DebitRate;
import com.psajd.banks.core.configuration.PercentageRates.DepositRate;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * bank rates configurations
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BankConfig {

    private CreditRate creditRate = new CreditRate();
    private DebitRate debitRate = new DebitRate();
    private DepositRate depositRate = new DepositRate();
    private LocalDate expirationDate = LocalDate.MAX;
}
