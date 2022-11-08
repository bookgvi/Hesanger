package com.example.hesanger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("activateMessage", "'");
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model) {
        model.addAttribute("activateMessage", "Fuck you");
        return "login";
    }
}
