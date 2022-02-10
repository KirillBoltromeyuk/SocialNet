package com.kirichproduction.messenger04.controller;

import com.kirichproduction.messenger04.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class PostController {

    private final PostService postService;

    public PostController( PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/addPost")
    @PreAuthorize("hasAuthority('users:read')")
    public String addPost() {
        return "addPost";
    }

    @PostMapping("/addPost")
    @PreAuthorize("hasAuthority('users:read')")
    public String savePost(@RequestParam String text) {
        postService.savePost(text);
        return "redirect:/user";
    }

    @GetMapping("/editPost/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public String postEdit(@PathVariable(value = "id") long id, Model model) {
        if (postService.thisAuthUsersPost(id)) {
            model.addAttribute("post", postService.postOptional(id));
            return "editPost";
        } else return "redirect:/user";
    }

    @PostMapping("/editPost/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public String postEditUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String text) {
        if (postService.thisAuthUsersPost(id)) {
            postService.postUpdate(id, text);
        }
        return "redirect:/user";
    }

    @PostMapping("/deletePost/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public String PostDelete(@PathVariable(value = "id") long id) {
        if (postService.thisAuthUsersPost(id)) {
            postService.postDelete(id);
        }
        return "redirect:/user";
    }
}
