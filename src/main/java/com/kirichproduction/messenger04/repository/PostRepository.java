package com.kirichproduction.messenger04.repository;

import com.kirichproduction.messenger04.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
    Iterable<Post> findAllByAuthorUsername(String authorUsername);
}
