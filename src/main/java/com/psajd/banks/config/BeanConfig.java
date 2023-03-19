package com.psajd.banks.config;

import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.core.time.TimeManager;
import com.psajd.banks.dao.AccountDao;
import com.psajd.banks.dao.BankDao;
import com.psajd.banks.dao.ClientDao;
import com.psajd.banks.services.AccountService;
import com.psajd.banks.services.BankService;
import com.psajd.banks.services.ClientService;
import com.psajd.banks.services.TimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.psajd.banks")
@EnableWebMvc
public class BeanConfig {

    @Bean
    public AccountDao accountDaoBean() {
        return new AccountDao();
    }

    @Bean
    public BankDao bankDaoBean() {
        return new BankDao();
    }

    @Bean
    public ClientDao clientDaoBean() {
        return new ClientDao();
    }

    @Bean
    public TimeManager timeManagerBean() {
        return new TimeManager();
    }

    @Bean
    public CentralBank centralBankBean() {
        return new CentralBank(timeManagerBean());
    }

    @Bean
    public AccountService accountServiceBean() {
        return new AccountService(accountDaoBean(), centralBankBean());
    }

    @Bean
    public BankService bankServiceBean() {
        return new BankService(bankDaoBean(), centralBankBean());
    }

    @Bean
    public ClientService ClientServiceBean() {
        return new ClientService(clientDaoBean(), centralBankBean());
    }

    @Bean
    public TimeService TimeServiceBean() {
        return new TimeService(centralBankBean());
    }

}
