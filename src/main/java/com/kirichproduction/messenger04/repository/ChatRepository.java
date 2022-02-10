package com.kirichproduction.messenger04.repository;

import com.kirichproduction.messenger04.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Iterable<Chat> findAllByUser1IdOrUser2Id(Long user1Id, Long user2Id);
    Optional<Chat> findByUser1IdAndUser2IdOrUser1IdAndUser2Id(Long user1Id, Long user2Id, Long user1Id2, Long user2Id2);
    Optional<Chat> findByUser1IdAndUser2Id(Long user1Id,Long user2Id);

}
