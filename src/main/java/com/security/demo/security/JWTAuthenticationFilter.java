package com.security.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.security.demo.constants.Constants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //private final String HEADER = "Authorization";
    //private final String PREFIX = "demo ";

    @Value("${jwt.token.secret}")
    private String SECRET_KEY;
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        //setFilterProcessesUrl();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
            // We parse the user's credentials and issue them to the AuthenticationManager
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), new ArrayList<>()));
            // Principal - credentials.getUsername()
            // Credentials - credentials.getPassword()
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // A user successfully logs in. We use this method to generate a JWT for this user.
        String token = JWT.create().withSubject((String) authResult.getPrincipal()).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(Algorithm.HMAC512(SECRET_KEY));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
