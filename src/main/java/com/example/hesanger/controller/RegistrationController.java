package com.example.hesanger.controller;

import com.example.hesanger.domain.User;
import com.example.hesanger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        boolean isCreated = userService.addUser(user);

        if(!isCreated) {
            model.put("message", "User already exist");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activation/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        String activateMessage = isActivated ? "Successfully activated" : "Activation code is not found";
        model.addAttribute("activateMessage", activateMessage);
        return "login";
    }
}
