package com.kirichproduction.messenger04.service;

import com.kirichproduction.messenger04.model.Post;
import com.kirichproduction.messenger04.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostService {
private final PostRepository postRepository;
private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Iterable<Post> postsById (Long id){
        Iterable<Post> posts = postRepository.findAllByAuthorId(id);
        return posts;
    }

    public void savePost(String text){
        Post post = new Post(text, userService.authUserId());
        postRepository.save(post);
    }

    public boolean thisAuthUsersPost (Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.get().getAuthorId().equals(userService.authUserId()))
            return true;
        return false;
    }

    public ArrayList<Post> postOptional (Long id){
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        return res;
    }

    public void postUpdate (Long id, String text){
        Post post = postRepository.findById(id).orElseThrow();
        post.setText(text);
        postRepository.save(post);
    }

    public void postDelete(Long id){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
    }
}
