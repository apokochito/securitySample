package com.security.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.security.demo.constants.Constants.SIGN_UP_URL;

@Slf4j
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsersDetailsService usersDetailsService;

    // Spring Security filter chain
    public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder, UsersDetailsService usersDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersDetailsService = usersDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We can define which resources are public and which are secured
        http.csrf()
                .disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                //We also configure custom security filter in the Spring Security filter chain
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }

    // We defined a custom implementation of UserDetailsService to load user-specific data in the security framework
    // We have also used this method to set the encrypt method used by our application (BCryptPasswordEncoder)
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersDetailsService).passwordEncoder(bCryptPasswordEncoder);
        log.info("WebSecurity - User authenticated from usersDetailsService");
    }
}
