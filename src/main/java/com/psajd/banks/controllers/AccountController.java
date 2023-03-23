package com.psajd.banks.controllers;

import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.accounts.AccountType;
import com.psajd.banks.core.bankEntities.Bank;
import com.psajd.banks.core.clients.Client;
import com.psajd.banks.services.AccountService;
import com.psajd.banks.services.BankService;
import com.psajd.banks.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/banks/{bankId}/clients/{clientId}/accounts")
public class AccountController {
    private final AccountService accountService;

    private final BankService bankService;

    private final ClientService clientService;

    @Autowired
    public AccountController(AccountService accountService, BankService bankService, ClientService clientService) {
        this.accountService = accountService;
        this.bankService = bankService;
        this.clientService = clientService;
    }

    @GetMapping("")
    public String getAccounts(Model model, @PathVariable String bankId, @PathVariable String clientId) {
        Bank bank = bankService.getBank(UUID.fromString(bankId));
        model.addAttribute("accountsList", accountService.getAccounts(bank, clientService.getClient(bank, UUID.fromString(clientId))));
        return "accounts/accountList";
    }

    @GetMapping("/new")
    public String newAccountForm(Model model, @PathVariable String bankId, @PathVariable String clientId) {
        model.addAttribute("accountType", "");
        return "accounts/newAccount";
    }

    @PostMapping("")
    public String addNewAccount(@RequestParam("accountType") String accountType, Model model, @PathVariable String bankId, @PathVariable String clientId) {
        AccountType type = switch (accountType) {
            case "credit" -> AccountType.CREDIT;
            case "debit" -> AccountType.DEBIT;
            case "deposit" -> AccountType.DEPOSIT;
            default -> throw new RuntimeException("type not supported");
        };

        Bank bank = bankService.getBank(UUID.fromString(bankId));
        Client client = clientService.getClient(bank, UUID.fromString(clientId));
        accountService.addNewAccount(bank, client, type);
        return "redirect:/banks/{bankId}/clients/{clientId}/accounts";
    }

    @GetMapping("/{accountId}")
    public String getAccount(Model model, @PathVariable String bankId, @PathVariable String clientId, @PathVariable String accountId) {
        Bank bank = bankService.getBank(UUID.fromString(bankId));
        Account account = accountService.getAccount(bank, UUID.fromString(clientId));
        model.addAttribute("account", account);
        return "accountTransatcion";
    }

    @DeleteMapping("/{accountId}")
    public String deleteAccount(Model model, @PathVariable String bankId, @PathVariable String clientId, @PathVariable String accountId) {
        Bank bank = bankService.getBank(UUID.fromString(bankId));
        Client client = clientService.getClient(bank, UUID.fromString(clientId));
        accountService.deleteAccount(bank, client, UUID.fromString(accountId));
        return "accounts/accountList";
    }
}
