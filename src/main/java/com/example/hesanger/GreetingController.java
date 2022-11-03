package com.example.hesanger;

import com.example.hesanger.Repos.MessageRepo;
import com.example.hesanger.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/")
    public String index(Map<String, Object> model) {
        model.put("messages", messageRepo.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String addRecord(
            @RequestParam String text,
            @RequestParam(defaultValue = "none") String tag,
            Map<String, Object> model
    ) {
        Message msg = new Message();
        msg.setText(text);
        msg.setTag(tag);
        messageRepo.save(msg);


        model.put("messages", messageRepo.findAll());
        return "index";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String tag, Map<String, Object> model) {
        Iterable<Message> messages;
        if (tag == null || tag.equals("*")) messages = messageRepo.findAll();
        else messages = messageRepo.findByTag(tag);
        model.put("messages", messages);
        return "index";
    }
}
