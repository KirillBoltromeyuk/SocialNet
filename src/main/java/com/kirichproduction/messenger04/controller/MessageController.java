package com.kirichproduction.messenger04.controller;


import com.kirichproduction.messenger04.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("")
    public String allContacts(Model model) {
        model.addAttribute("messages", messageService.allContacts());
        return "allContacts";
    }

    @GetMapping("/{destinationId}")
    public String showMessages(@PathVariable Long destinationId,
                               Model model) {
        model.addAttribute("messages", messageService.showMessages(destinationId));
        model.addAttribute("destinationId", destinationId);
        return "messages";
    }

    @PostMapping("/{destinationId}")
    public String sendMessage(@RequestParam String text,
                              @PathVariable Long destinationId) {
        messageService.sendMessage(text, destinationId);
        return "redirect:/user/message/" + destinationId;
    }
}
