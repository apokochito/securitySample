package com.security.demo.controller;

import com.security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
public class AppController {

    @Autowired
    public UserRepository userRepository;

    @GetMapping(value = "/greeting")
    public String getAllUsers(@RequestParam(value = "name", defaultValue = "World") String name) {

        // userRepository.findAll();
        // 1. Generating JWT - POST, if a valid user is provided, token will be generated.
        // 2. Validating JWT - GET, return resource if there is a valid JWT token from POST method.

        return "Hello " + name + " !";
    }
}
