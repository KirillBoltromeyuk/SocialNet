package com.kirichproduction.messenger04.controller;

import com.kirichproduction.messenger04.service.ChatService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user/chats")
@PreAuthorize("hasAuthority('users:read')")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("")
    public String chats (Model model){
        model.addAttribute("chats",chatService.chatsFromUser());
        return "chats";
    }

    @GetMapping("/{id}")
    public String chatFromId (@PathVariable Long id, Model model) {
        if (chatService.userPresentInChat(id)) {
            model.addAttribute("messages", chatService.getMessagesFromChatId(id));
            model.addAttribute("chatId", id);
            model.addAttribute("chatName", chatService.getChatNameWithChatId(id));
            return "chat";
        }else return "redirect:/user/chats";
    }
    @PostMapping("/{id}")
    public String sendMessageFromChatById(@RequestParam String text,
                                          @PathVariable Long id){
        chatService.sendMessageFromChatId(text, id);
        return "redirect:/user/chats/"+id;
    }

    @GetMapping("/byUser/{id}")
    public String openChatWithUser(@PathVariable Long id){
        return "redirect:/user/chats/"+chatService.findChatWithUserId(id);
    }
}
