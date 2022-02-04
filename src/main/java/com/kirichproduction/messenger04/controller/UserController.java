package com.kirichproduction.messenger04.controller;

import com.kirichproduction.messenger04.model.Post;
import com.kirichproduction.messenger04.model.User;
import com.kirichproduction.messenger04.repository.PostRepository;
import com.kirichproduction.messenger04.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public String userPage(@PathVariable(value = "id") long id, Model model) {
        if (!userRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);

        Iterable<Post> posts = postRepository.findAllByAuthorUsername(user.get().getEmail());
        model.addAttribute("posts", posts);

        return "userPage";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('users:read')")
    public String userHomePage(Model model) {
        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(authUser.getUsername());
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);

        Iterable<Post> posts = postRepository.findAllByAuthorUsername(user.get().getEmail());
        model.addAttribute("posts", posts);

        return "userHomePage";
    }

    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "allUsers";
    }
}
