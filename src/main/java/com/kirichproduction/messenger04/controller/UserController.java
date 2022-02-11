package com.kirichproduction.messenger04.controller;
import com.kirichproduction.messenger04.model.Image;
import com.kirichproduction.messenger04.model.User;
import com.kirichproduction.messenger04.service.FileUploadUtil;
import com.kirichproduction.messenger04.service.ImageService;
import com.kirichproduction.messenger04.service.PostService;
import com.kirichproduction.messenger04.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final ImageService imageService;

    public UserController(UserService userService, PostService postService, ImageService imageService) {
        this.userService = userService;
        this.postService = postService;
        this.imageService = imageService;
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

    @GetMapping("/user/edit")
    @PreAuthorize("hasAuthority('users:read')")
    public String userEdit(Model model){
        model.addAttribute("authUser",userService.userDetails(userService.authUserId()));
        return "userEditPage";
    }

    @PostMapping("/user/edit")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            userService.updateUser(firstName, lastName, userService.userById(userService.authUserId()).get().getImageId());
            return "redirect:/user";
        }
        Image image = imageService.saveImage(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        userService.updateUser(firstName, lastName, image.getId());
        FileUploadUtil.saveFile("images/",image.getImageName() , multipartFile);
        return "redirect:/user";

    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('users:read')")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "allUsers";
    }
}
