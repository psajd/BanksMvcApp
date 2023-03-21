package com.psajd.banks.controllers;

import com.psajd.banks.services.BankService;
import com.psajd.banks.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/banks/{bankId}/clients")
public class ClientController {

    private ClientService clientService;
    private BankService bankService;

    @Autowired
    public ClientController(ClientService clientService, BankService bankService) {
        this.clientService = clientService;
        this.bankService = bankService;
    }

    @GetMapping("")
    public String getClients(Model model, @PathVariable String bankId) {
        model.addAttribute("bank", bankService.getBank(UUID.fromString(bankId)));
        return "clients/clientsList";
    }

    @GetMapping("/new")
    public String newClientForm() {
        return "clients/newClient";
    }

    @PostMapping("")
    public String addNewClient() {
        return "redirect:/";
    }

    @GetMapping("/{clientId}")
    public String getClient(@PathVariable String clientId) {
        return "clients/client";
    }

    @GetMapping("/{clientId}/edit/")
    public String editBank(Model model, @PathVariable String clientId) {
        return "clients/clientEdit";
    }

    @PatchMapping("/{clientId}")
    public String updateClient(@PathVariable String clientId) {
        return "redirect:{id}";
    }

    @DeleteMapping("/{clientId}")
    public String deleteClient(@PathVariable String clientId) {
        return "redirect:/clients";
    }
}
