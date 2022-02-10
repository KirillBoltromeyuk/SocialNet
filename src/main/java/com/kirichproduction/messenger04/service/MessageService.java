package com.kirichproduction.messenger04.service;

import com.kirichproduction.messenger04.model.Message;
import com.kirichproduction.messenger04.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Iterable<Message> showMessages (Long destinationId){
    Iterable<Message> messages = messageRepository.findAllByAuthorIdAndDestinationIdOrAuthorIdAndDestinationIdOrderById(
            userService.authUserId(), destinationId, destinationId, userService.authUserId());
    return messages;
    }

    public void sendMessage (String text, Long destinationId){
        Message message = new Message(text, userService.authUserId(), destinationId);
        messageRepository.save(message);
    }

    public List<Message> allContacts(){
        List<Message> messages = messageRepository.findDistinctByAuthorIdOrderByDestinationId(userService.authUserId());

        return messages;
    }
}
