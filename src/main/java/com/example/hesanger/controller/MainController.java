package com.example.hesanger.controller;

import com.example.hesanger.Repos.MessageRepo;
import com.example.hesanger.domain.Message;
import com.example.hesanger.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping({"/", "/home"})
    public String index(Map<String, Object> model) {
        Authentication authPrincipal = SecurityContextHolder.getContext().getAuthentication();
        String authorName = authPrincipal.getName();

        model.put("name", authorName);
        return "home";
    }

    @GetMapping("/main")
    public String filter(@RequestParam(defaultValue = "") String tag, Map<String, Object> model) {
        Iterable<Message> messages;
        if (tag == null || tag.isEmpty() || tag.equals("*")) {
            messages = messageRepo.findAll();
        } else {
            tag = tag.equals(" ") ? "" : tag;
            messages = messageRepo.findByTag(tag);
        }
        model.put("messages", messages);
        model.put("tag", tag);
        return "main";
    }

//    @GetMapping("/main")
//    public String greeting(Map<String, Object> model) {
//        Iterable<Message> messages = messageRepo.findAll();
//        model.put("messages", messages);
//        return "main";
//    }

    @PostMapping("/add")
    public String addRecord(
            @AuthenticationPrincipal User author,
            @RequestParam String text,
            @RequestParam(defaultValue = "") String tag,
            Map<String, Object> model
    ) {
        Message msg = new Message(text, tag, author);
        messageRepo.save(msg);

        model.put("messages", messageRepo.findAll());
        model.put("tag", "");
        return "main";
    }

}
