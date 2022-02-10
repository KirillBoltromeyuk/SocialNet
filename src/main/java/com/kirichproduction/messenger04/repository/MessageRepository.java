package com.kirichproduction.messenger04.repository;

import com.kirichproduction.messenger04.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Iterable<Message> findAllByChatId(Long chatId);
}
