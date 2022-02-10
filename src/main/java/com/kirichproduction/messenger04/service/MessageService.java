package com.kirichproduction.messenger04.service;

import com.kirichproduction.messenger04.model.Message;
import com.kirichproduction.messenger04.repository.MessageRepository;
import org.springframework.stereotype.Service;


@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }


    public void sendMessage (String text, Long chatId){
        Message message = new Message(text, userService.authUserId(), chatId);
        messageRepository.save(message);
    }


    public Iterable<Message> messagesByChatId(Long id){
        Iterable<Message> messages = messageRepository.findAllByChatId(id);
        return messages;
    }
}
