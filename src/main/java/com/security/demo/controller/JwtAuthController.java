package com.security.demo.controller;

import com.security.demo.model.User;
import com.security.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    // REST controller to implement the authentication process through a username/password login

    @PostMapping("/singup")
    public String singUp(@RequestBody User user) {
        // What if someone catch the password before this step?
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Registered";
    }

}
