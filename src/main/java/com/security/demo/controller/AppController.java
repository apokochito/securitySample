package com.security.demo.controller;

import com.security.demo.model.User;
import com.security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/security")
public class AppController {

    @Autowired
    public UserRepository userRepository;

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/users", produces = "application/json")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
}
