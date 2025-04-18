package ru.spshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ShopController {

    @ModelAttribute
    public void addBaseApiUrl(Model model) {
        model.addAttribute("baseApiUrl", System.getProperty("BASE_API_URL"));
    }

    @GetMapping
    public String getMainPage() {
        return "main";
    }

    @GetMapping("/findSameName")
    public String getFindSameNamePage() {
        return "findSameName";
    }

    @GetMapping("/uraltoys")
    public String getUraltoysPage() {
        return "uralToys";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "contact";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin";
    }

}
