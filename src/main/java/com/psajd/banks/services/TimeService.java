package com.psajd.banks.services;

import com.psajd.banks.core.bankEntities.CentralBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TimeService {

    private CentralBank centralBank;

    @Autowired
    public TimeService(CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    public LocalDate getCurrentDate() {
        return centralBank.getTimeManager().getNow();
    }

    public LocalDate AddTime(int years, int months, int days) {
        centralBank.getTimeManager().Add(years, months, days);
        return getCurrentDate();
    }
}
