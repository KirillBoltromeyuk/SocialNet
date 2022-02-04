package com.kirichproduction.messenger04.controller;

import com.kirichproduction.messenger04.model.Post;
import com.kirichproduction.messenger04.repository.PostRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/addPost")
    @PreAuthorize("hasAuthority('users:read')")
    public String addPost() {
        return "addPost";
    }

    @PostMapping("/addPost")
    @PreAuthorize("hasAuthority('users:read')")
    public String savePost(@RequestParam String text, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post(text, user.getUsername());
        postRepository.save(post);
        return "redirect:/user";
    }

    @GetMapping("/editPost/{id}")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Optional<Post> post = postRepository.findById(id);
        if (post.get().getAuthorUsername().equals(authUser.getUsername())) {
            ArrayList<Post> res = new ArrayList<>();
            post.ifPresent(res::add);
            model.addAttribute("post", res);
            return "editPost";
        } else return "redirect:/user";
    }

    @PostMapping("/editPost/{id}")
    public String blogEditUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setText(text);
        postRepository.save(post);
        return "redirect:/user";
    }

    @PostMapping("/deletePost/{id}")
    public String blogPostDelete(@PathVariable(value = "id") long id,
                                 Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/user";
    }
}
