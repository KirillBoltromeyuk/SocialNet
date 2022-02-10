package com.kirichproduction.messenger04.service;

import com.kirichproduction.messenger04.model.Chat;
import com.kirichproduction.messenger04.model.Message;
import com.kirichproduction.messenger04.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;


@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }


    public void sendMessage (String text, Long chatId){
        Message message = new Message(text, userService.authUserId(), chatId, false);
        messageRepository.save(message);
    }


    public Iterable<Message> messagesByChatId(Long id){
        Iterable<Message> messages = messageRepository.findAllByChatId(id);
        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()){
            Message message = iterator.next();
            if (!userService.authUserId().equals(message.getAuthorId())) {
                message.setRead(true);
                messageRepository.save(message);
            }else if(message.getChat().getUser1Id().equals(message.getChat().getUser2Id())){
                message.setRead(true);
                messageRepository.save(message);
            }
        }
        return messages;
    }

    public int notReadMessagesCountFromUserIdFromChatId (Long userId, Long chatId){
        return messageRepository.countAllByAuthorIdAndChatIdAndIsRead(userId, chatId, false);
    }
}
