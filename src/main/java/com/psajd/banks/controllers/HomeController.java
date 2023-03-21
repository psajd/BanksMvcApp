package com.psajd.banks.controllers;

import com.psajd.banks.core.bankEntities.CentralBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final CentralBank centralBank;

    @Autowired
    public HomeController(CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    public String handleNotFoundException() {
        return "";
    }

    @GetMapping
    public String homePage() {
        return "/index";
    }
}
