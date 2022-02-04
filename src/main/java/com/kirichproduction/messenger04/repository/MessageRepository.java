package com.kirichproduction.messenger04.repository;

import com.kirichproduction.messenger04.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Iterable<Message> findAllByAuthorUsernameAndDestinationUsernameOrAuthorUsernameAndDestinationUsername(String author1Username, String destination1Username,
                                                                                                          String author2Username, String destination2Username);

    Iterable<Message> findAllByAuthorUsernameOrDestinationUsername(String authorUsername, String destinationUsername);
}
