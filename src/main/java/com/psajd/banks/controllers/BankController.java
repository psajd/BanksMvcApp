package com.psajd.banks.controllers;

import com.psajd.banks.core.bankEntities.Bank;
import com.psajd.banks.core.configuration.BankConfig;
import com.psajd.banks.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/banks")
public class BankController {

    private BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("")
    public String getBanks(Model model) {
        List<Bank> banks = bankService.getBanks();
        model.addAttribute("banks", banks);
        return "banks/banksList";
    }

    @GetMapping("/new")
    public String newBankForm(Model model) {
        Bank bank = new Bank();
        bank.setBankConfig(new BankConfig());
        bank.setId(UUID.randomUUID());
        model.addAttribute("bank", bank);
        return "banks/newBank";
    }

    /*@RequestParam("name") String name, @RequestParam("name") String creditLowerBorder,
                             @RequestParam("name") String creditUpperBorder, @RequestParam("name") String creditCommission,
                             @RequestParam("name") String debitUpperBorder, @RequestParam("name") String debitPercentage,
                             @RequestParam("name") String depositLowerPercentage, @RequestParam("name") String depositMiddleBorder,
                             @RequestParam("name") String depositMiddlePercentage, @RequestParam("name") String depositUpperBorder,
                             @RequestParam("name") String depositUpperPercentage*/
    @PostMapping("")
    public String addNewBank(@ModelAttribute Bank bank) {
        bank.setId(UUID.randomUUID());
        bankService.addNewBank(bank);
        return "redirect:/banks";
    }

    @GetMapping("/{id}")
    public String getBank(@PathVariable String id, Model model) {
        Bank bank = bankService.getBank(UUID.fromString(id));
        model.addAttribute("bank", bank);
        return "banks/bank";
    }

    @GetMapping("/{id}/edit/")
    public String editBank(Model model, @PathVariable String id) {
        Bank bank = bankService.getBank(UUID.fromString(id));
        model.addAttribute("bank", bank);
        return "banks/bankEdit";
    }

    @PatchMapping("/{id}")
    public String updateBank(@PathVariable String id, Model model, @ModelAttribute Bank bank) {
        Bank findBank = bankService.getBank(UUID.fromString(id));
        bankService.updateBank(bank);
        return "redirect:{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBank(@PathVariable String id) {
        bankService.deleteBank(UUID.fromString(id));
        return "redirect:/banks";
    }

}
