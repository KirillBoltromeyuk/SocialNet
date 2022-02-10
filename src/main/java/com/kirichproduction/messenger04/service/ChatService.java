package com.kirichproduction.messenger04.service;

import com.kirichproduction.messenger04.model.Chat;
import com.kirichproduction.messenger04.model.Message;
import com.kirichproduction.messenger04.model.User;
import com.kirichproduction.messenger04.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;
    private final MessageService messageService;

    public ChatService(ChatRepository chatRepository, UserService userService, MessageService messageService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
        this.messageService = messageService;
    }

    public Iterable<Chat> chatsFromUser(){
        Iterable<Chat> chats = chatRepository.findAllByUser1IdOrUser2Id(userService.authUserId(), userService.authUserId());
        Iterator<Chat> iterator = chats.iterator();
        while (iterator.hasNext()){
            Chat chat = iterator.next();
            if(userService.authUserId().equals(chat.getUser1Id())) chat.setChatName(userService.userById(chat.getUser2Id()).get().getFirstName()
                                                                                    + " " + userService.userById(chat.getUser2Id()).get().getLastName());
            else chat.setChatName(userService.userById(chat.getUser1Id()).get().getFirstName()
                                    + " " + userService.userById(chat.getUser1Id()).get().getLastName());
        }
        return chats;
    }

    public Iterable<Message> getMessagesFromChatId(Long id){
        return messageService.messagesByChatId(id);
    }

    public boolean userPresentInChat(Long chatId){
        if(userService.authUserId().equals(chatRepository.findById(chatId).get().getUser1Id())||
                userService.authUserId().equals(chatRepository.findById(chatId).get().getUser2Id()))
            return true;
        else return false;
    }

    public void sendMessageFromChatId(String text, Long id){
        messageService.sendMessage(text, id);
    }

    public Long findChatWithUserId(Long id){

        Optional<Chat> chat1 = chatRepository.findByUser1IdAndUser2IdOrUser1IdAndUser2Id(userService.authUserId(), id, id, userService.authUserId());
        if (chat1.isPresent()) return chat1.get().getId();
        else {
            Chat chat2 = new Chat(userService.authUserId(), id);
            chatRepository.save(chat2);
            return chatRepository.findByUser1IdAndUser2Id(userService.authUserId(), id).get().getId();
        }

    }
}
