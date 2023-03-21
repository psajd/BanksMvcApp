package com.psajd.banks.services;

import com.psajd.banks.core.bankEntities.Bank;
import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.core.configuration.BankConfig;
import com.psajd.banks.core.time.TimeManager;
import com.psajd.banks.dao.AccountDao;
import com.psajd.banks.dao.BankDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BankService {

    private CentralBank centralBank;
    private BankDao bankDao;

    @Autowired
    public BankService(BankDao bankDao, CentralBank centralBank) {
        this.bankDao = bankDao;
        this.centralBank = centralBank;
    }

    public Bank addNewBank(Bank bank) {
        centralBank.addBank(bank);
        return bank;
    }

    public List<Bank> getBanks() {
        return centralBank.getBanks();
    }

    public Bank getBank(UUID uuid) {
        return centralBank.findBank(uuid);
    }

    public void deleteBank(UUID uuid) {
        centralBank.deleteBank(uuid);
    }

    public Bank updateBank(Bank bank) {
        return centralBank.updateBank(bank);
    }
}
