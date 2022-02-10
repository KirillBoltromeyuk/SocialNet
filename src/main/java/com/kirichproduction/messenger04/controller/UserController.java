package com.kirichproduction.messenger04.controller;
import com.kirichproduction.messenger04.service.PostService;
import com.kirichproduction.messenger04.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;

    public UserController( UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public String userPage(@PathVariable(value = "id") long id, Model model) {
        if (!userService.userExistById(id)) {
            return "redirect:/";
        }
        model.addAttribute("user", userService.userDetails(id));
        model.addAttribute("posts", postService.postsById(id));
        return "userPage";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('users:read')")
    public String userHomePage(Model model) {

        model.addAttribute("user", userService.userDetails(userService.authUserId()));
        model.addAttribute("posts", postService.postsById(userService.authUserId()));
        return "userHomePage";
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('users:read')")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "allUsers";
    }
}
