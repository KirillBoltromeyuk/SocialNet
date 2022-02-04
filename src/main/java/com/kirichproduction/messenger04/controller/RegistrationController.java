package com.kirichproduction.messenger04.controller;

import com.kirichproduction.messenger04.model.Role;
import com.kirichproduction.messenger04.model.Status;
import com.kirichproduction.messenger04.model.User;
import com.kirichproduction.messenger04.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "registrationPage";
        if (user.getPassword().equals(user.getPasswordConfirm())) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
            User newUser = new User(user.getEmail(), user.getFirstName(), user.getLastName(), bCryptPasswordEncoder.encode(user.getPassword()), Role.USER, Status.ACTIVE);
            userRepository.save(newUser);
            return "redirect:/login";
        } else {
            user.setPasswordCheck(false);
            return "registrationPage";
        }


    }
}