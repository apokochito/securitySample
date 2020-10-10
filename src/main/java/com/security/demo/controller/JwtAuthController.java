package com.security.demo.controller;

import com.security.demo.model.User;
import com.security.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/access")
public class JwtAuthController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public JwtAuthController(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    // Create user route
    @PostMapping("/singup")
    public String singUp(@RequestBody User user) {
        // Encode password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        log.info("Controller - Password encoded");
        // Save user into database
        userRepository.save(user);
        log.info("Controller - User saved");
        return "User registered, welcome!";
    }

}
