package ru.spshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class ShopController {

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
