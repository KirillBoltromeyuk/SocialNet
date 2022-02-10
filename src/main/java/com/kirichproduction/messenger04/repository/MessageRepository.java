package com.kirichproduction.messenger04.repository;

import com.kirichproduction.messenger04.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    Iterable<Message> findAllByAuthorIdAndDestinationIdOrAuthorIdAndDestinationIdOrderById(Long author1Id, Long destination1Id,
                                                                                  Long author2Id, Long destination2Id);


    List<Message> findDistinctByAuthorIdOrderByDestinationId(Long authorId);







}
