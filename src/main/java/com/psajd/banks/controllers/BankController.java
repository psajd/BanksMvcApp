package com.psajd.banks.controllers;

import com.psajd.banks.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/banks")
public class BankController {
    private BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public String getBanks() {
        return "";
    }

    /*@PostMapping
    public String addNewBank() {

    }

    public String getBank() {
        return "";
    }

    public*/
}
