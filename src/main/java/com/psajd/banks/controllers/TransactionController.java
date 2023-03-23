package com.psajd.banks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @GetMapping("")
    public String getTransactionForm() {
        return "transactions/transaction";
    }

    @PostMapping("")
    public String doTransaction() {
        return "redirect:/transactions";
    }
}
