package com.psajd.banks.controllers;

import com.psajd.banks.core.bankEntities.Bank;
import com.psajd.banks.core.clients.Client;
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
        Bank bank = bankService.getBank(UUID.fromString(bankId));
        model.addAttribute("bank", bank);
        model.addAttribute("header", "Clients from " + bank.getBankName() + " bank");
        return "clients/clientsList";
    }

    @GetMapping("/new")
    public String newClientForm(@PathVariable String bankId, Model model) {
        if (bankService.getBank(UUID.fromString(bankId)) == null) {
            throw new RuntimeException("bank not found");
        }
        Client client = new Client();
        client.setId(UUID.randomUUID());
        model.addAttribute("client", new Client());
        return "clients/newClient";
    }

    @PostMapping("")
    public String addNewClient(@PathVariable String bankId, @ModelAttribute Client client) {
        if (client.getClientName().isBlank() || client.getPassport().isBlank()) {
            throw new RuntimeException("name or passport is null");
        }
        // FIXME: 21.03.2023 не забыть убрать
        client.setClientName("name");
        client.setAddress("address");
        client.setPhoneNumber("phoneNumber");
        client.setPassport("passport");
        clientService.addNewClient(bankService.getBank(UUID.fromString(bankId)), client);
        return "redirect:/banks/{bankId}/clients";
    }

    @GetMapping("/{clientId}")
    public String getClient(Model model, @PathVariable String bankId, @PathVariable String clientId) {
        Bank bank = bankService.getBank(UUID.fromString(bankId));
        Client client = clientService.getClient(bank, UUID.fromString(clientId));
        model.addAttribute("client", client);
        return "clients/client";
    }

    @GetMapping("/{clientId}/edit/")
    public String editClient(@PathVariable String bankId, Model model, @PathVariable String clientId) {
        Bank bank = bankService.getBank(UUID.fromString(bankId));
        Client client = clientService.getClient(bank, UUID.fromString(clientId));
        model.addAttribute("client", client);
        return "clients/clientEdit";
    }

    @PatchMapping("/{clientId}")
    public String updateClient(@PathVariable String bankId, @PathVariable String clientId, @ModelAttribute Client client) {
        Bank bank = bankService.getBank(UUID.fromString(bankId));
        client.setId(UUID.fromString(clientId));
        clientService.updateClient(bank, client);
        return "redirect:/banks/{bankId}/clients/{clientId}";
    }

    @DeleteMapping("/{clientId}")
    public String deleteClient(@PathVariable String bankId, @PathVariable String clientId) {
        Bank bank = bankService.getBank(UUID.fromString(bankId));
        clientService.deleteClient(bank, UUID.fromString(clientId));
        return "redirect:/banks/{bankId}/clients";
    }
}
