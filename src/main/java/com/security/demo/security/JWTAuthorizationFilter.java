package com.security.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.security.demo.constants.Constants.*;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    // Filter responsible for user authorization
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        log.info("JWTAuthorizationFilter - Headers received");
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        log.info("JWTAuthorizationFilter - Headers validated");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        log.info("JWTAuthorizationFilter - Authentication succeeded");
        // Set the user in the SecurityContext and allow the request to move on
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("JWTAuthorizationFilter - User set in the SecurityContext");
        chain.doFilter(request, response);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // Parse the token
        String token = request.getHeader(HEADER_STRING);
        log.info("Token received");
        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            log.info("Token validated");
            if (user != null) {
                log.info("User validated");
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
