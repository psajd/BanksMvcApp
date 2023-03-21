package com.psajd.banks.controllers;

import com.psajd.banks.services.BankService;
import com.psajd.banks.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/banks/{bankId}/clients/{clientId}/notifications")
public class NotificationController {
    private final ClientService clientService;
    private final BankService bankService;

    @Autowired
    public NotificationController(ClientService clientService, BankService bankService) {
        this.clientService = clientService;
        this.bankService = bankService;
    }


    @GetMapping("")
    public String getNotifications(Model model, @PathVariable String bankId, @PathVariable String clientId) {
        model.addAttribute("client", clientService.getClient(bankService.getBank(UUID.fromString(bankId)), UUID.fromString(clientId)));
        return "notifications/notification";
    }
}
