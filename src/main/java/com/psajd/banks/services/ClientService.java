package com.psajd.banks.services;

import com.psajd.banks.core.bankEntities.Bank;
import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.core.clients.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ClientService {
    private CentralBank centralBank;

    @Autowired
    public ClientService(CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    public Client addNewClient(Bank bank, Client client) {
        centralBank.addClientToBank(bank, client);
        return client;
    }

    public List<Client> getClients(Bank bank) {
        return bank.getClients();
    }

    public Client getClient(Bank bank, UUID uuid) {
        return bank.getClient(uuid);
    }

    public void deleteClient(Bank bank, UUID uuid) {
        bank.removeClient(uuid);
    }

    public Client updateClient(Bank bank, Client client) {
        return bank.updateClient(client);
    }
}
