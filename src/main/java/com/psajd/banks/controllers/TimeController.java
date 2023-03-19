package com.psajd.banks.controllers;

import com.psajd.banks.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/time")
public class TimeController {

    private TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("")
    public String getCurrentTime(Model model) {
        model.addAttribute("currentTime", timeService.getCurrentDate());
        return "time/index";
    }

    @PostMapping("/add")
    public String addTime(String years, String months, String days, Model model) {
        try {
            int intYears = years.equals("") ? 0 : Integer.parseInt(years);
            int intMonth = months.equals("") ? 0 : Integer.parseInt(months);
            int intDays = days.equals("") ? 0 : Integer.parseInt(days);

            timeService.AddTime(intYears, intMonth, intDays);

        } catch (Exception e) {
            model.addAttribute("exception", e.getMessage());
            return "time/errorAdd";
        }
        return "time/successAdd";
    }
}
