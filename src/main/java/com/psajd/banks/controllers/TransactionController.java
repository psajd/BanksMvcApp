package com.psajd.banks.controllers;

import com.psajd.banks.core.accounts.Account;
import com.psajd.banks.core.transactions.*;
import com.psajd.banks.services.AccountService;
import com.psajd.banks.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("")
    public String getTransactionForm(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        model.addAttribute("onDone", TransactionStatus.ON_DONE);
        return "transactions/transaction";
    }

    @PostMapping("/replenishment")
    public String doReplenishment(@RequestParam String srcAccountId, @RequestParam(name = "moneyAmount") String moneyAmount) {
        Account account = accountService.getAccount(UUID.fromString(srcAccountId));
        new ReplenishmentTransaction(account, Double.parseDouble(moneyAmount), UUID.randomUUID()).doTransaction();
        return "redirect:/transactions";
    }

    @PostMapping("/withdraw")
    public String doWithdraw(@RequestParam String srcAccountId, @RequestParam(name = "moneyAmount") String moneyAmount) {
        Account account = accountService.getAccount(UUID.fromString(srcAccountId));
        new WithdrawTransaction(account, Double.parseDouble(moneyAmount), UUID.randomUUID()).doTransaction();
        return "redirect:/transactions";
    }

    @PostMapping("/transfer")
    public String doTransfer(@RequestParam String srcAccountId, @RequestParam String dstAccountId, @RequestParam(name = "moneyAmount") String moneyAmount) {
        Account srcAccount = accountService.getAccount(UUID.fromString(srcAccountId));
        Account destAccount = accountService.getAccount(UUID.fromString(dstAccountId));
        new TransferTransaction(srcAccount, destAccount, Double.parseDouble(moneyAmount), UUID.randomUUID()).doTransaction();
        return "redirect:/transactions";
    }

    @GetMapping("/replenishment")
    public String doReplenishmentForm() {
        return "transactions/replenishmentForm";
    }

    @GetMapping("/withdraw")
    public String doWithdrawForm() {
        return "transactions/withdrawForm";
    }

    @GetMapping("/transfer")
    public String doTransferForm() {
        return "transactions/transferForm";
    }

    @PatchMapping("/{transactionId}")
    public String undoTransaction(@PathVariable String transactionId) {
        Transaction transaction = transactionService.findTransaction(UUID.fromString(transactionId));
        transaction.undo();
        return "redirect:/transactions";
    }
}
