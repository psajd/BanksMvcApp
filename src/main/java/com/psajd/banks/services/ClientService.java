package com.psajd.banks.services;

import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.dao.AccountDao;
import com.psajd.banks.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientService {
    private CentralBank centralBank;
    private ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao, CentralBank centralBank) {
        this.clientDao = clientDao;
        this.centralBank = centralBank;
    }
}
