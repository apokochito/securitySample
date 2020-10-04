package com.security.demo.controller;

import com.security.demo.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class JwtAuthController {

    // REST controller to implement the authentication process through a username/password login

    @PostMapping("/user")
    public User login(@RequestParam(value = "username") String username, @RequestParam("password") String password) {
        String token = getJWTToken(username);
        User user = new User();
        user.setUsername(username);
        user.setToken(token);
        return user;
    }

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
