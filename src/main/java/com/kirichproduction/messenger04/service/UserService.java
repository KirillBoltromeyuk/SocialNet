package com.kirichproduction.messenger04.service;

import com.kirichproduction.messenger04.model.User;
import com.kirichproduction.messenger04.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> userDetails(Long id){
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        return res;
    }

    public Long authUserId (){
        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(authUser.getUsername());
        return user.get().getId();
    }

    public Iterable<User> findAllUsers(){
        Iterable<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    public boolean userExistById (Long id) {
        if (userRepository.existsById(id))  return true;
        return false;

    }
}
