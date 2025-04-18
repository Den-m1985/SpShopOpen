package ru.spshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @ModelAttribute
    public void addBaseApiUrl(Model model) {
        model.addAttribute("baseApiUrl", System.getProperty("BASE_API_URL"));
    }

    @GetMapping
    public String getAccountPage() {
        return "account";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }
}
