package com.security.demo.controller;

import com.security.demo.model.User;
import com.security.demo.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/login")
    public User login(@RequestBody User user) {

        // What if someone catch the password before this step?
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }


    /*String token = getJWTToken(username);
        User user = new User();
        user.setUsername(username);
        user.setToken(token);*/

    private String getJWTToken(String username) {

        // We need to build the token, delegating the Jwts in the utility class that
        // includes information about its expiration and a Spring GrantedAuthority object that,
        // as we will see later, will be used to authorize requests to protected resources.

        String secretKey = "ThisIsASecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("demoJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "demo " + token;
    }

}
