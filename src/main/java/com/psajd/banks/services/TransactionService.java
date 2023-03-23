package com.psajd.banks.services;

import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.core.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionService {

    private CentralBank centralBank;

    @Autowired
    public TransactionService(CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    public List<Transaction> getAllTransactions() {
        return centralBank.getAccounts().stream().flatMap(x -> x.getTransactions().stream()).toList();
    }

    public Transaction findTransaction(UUID uuid) {
        return getAllTransactions().stream().filter(x -> x.getId().equals(uuid)).findFirst().orElse(null);
    }
}
