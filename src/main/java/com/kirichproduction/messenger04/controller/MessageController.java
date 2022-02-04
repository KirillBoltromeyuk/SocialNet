package com.kirichproduction.messenger04.controller;

import com.kirichproduction.messenger04.model.Message;
import com.kirichproduction.messenger04.repository.MessageRepository;
import com.kirichproduction.messenger04.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user/message")
public class MessageController {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String allContacts() {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Message> messages = messageRepository.findAllByAuthorUsernameOrDestinationUsername(authUser.getUsername(), authUser.getUsername());
        // TODO : add messages
        return "allContacts";
    }

    @GetMapping("/{destinationUsername}")
    public String showMessages(@PathVariable String destinationUsername,
                               Model model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Message> messages = messageRepository.findAllByAuthorUsernameAndDestinationUsernameOrAuthorUsernameAndDestinationUsername(
                authUser.getUsername(), destinationUsername, destinationUsername, authUser.getUsername());
        model.addAttribute("messages", messages);
        model.addAttribute("destinationUsername", destinationUsername);
        return "messages";
    }

    @PostMapping("/{destinationUsername}")
    public String sendMessage(@RequestParam String text,
                              @PathVariable String destinationUsername, Model model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Message message = new Message(text, authUser.getUsername(), destinationUsername);
        messageRepository.save(message);
        return "redirect:/user/message/" + destinationUsername;
    }
}
