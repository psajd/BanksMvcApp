package com.psajd.banks.config;

import com.psajd.banks.core.bankEntities.CentralBank;
import com.psajd.banks.core.time.TimeManager;
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
    public TimeManager timeManagerBean() {
        return new TimeManager();
    }

    @Bean
    public CentralBank centralBankBean() {
        return new CentralBank(timeManagerBean());
    }

    @Bean
    public AccountService accountServiceBean() {
        return new AccountService(centralBankBean());
    }

    @Bean
    public BankService bankServiceBean() {
        return new BankService(centralBankBean());
    }

    @Bean
    public ClientService ClientServiceBean() {
        return new ClientService(centralBankBean());
    }

    @Bean
    public TimeService TimeServiceBean() {
        return new TimeService(centralBankBean());
    }

}
