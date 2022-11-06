package com.example.hesanger.controller;

import com.example.hesanger.Repos.UserRepo;
import com.example.hesanger.domain.Role;
import com.example.hesanger.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String userList(Model model) {
        List<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam("userId") User user,
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            Model model) {
        user.setUsername(username);
        user.getRoles().clear();
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        for (String formKey : form.keySet()) {
            if (roles.contains(formKey)) {
                user.getRoles().add(Role.valueOf(formKey));
            }
        }
        userRepo.save(user);
        return "redirect:/user";
    }
}
