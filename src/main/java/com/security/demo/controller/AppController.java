package com.security.demo.controller;

import com.security.demo.model.User;
import com.security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/security")
public class AppController {

    @Autowired
    public UserRepository userRepository;

    @GetMapping(value = "/users")
    public String getAllUsers() {
        // userRepository.findAll();
        // 1. Generating JWT - POST, if a valid user is provided, token will be generated.
        // 2. Validating JWT - GET, return resource if there is a valid JWT token from POST method.
        return "Hello";
    }

    @PostMapping(value = "/users", produces = "application/json")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
}
