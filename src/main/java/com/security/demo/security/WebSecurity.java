package com.security.demo.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.security.demo.constants.Constants.SIGN_UP_URL;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsersDetailsService usersDetailsService;

    public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder, UsersDetailsService usersDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersDetailsService = usersDetailsService;
    }

    public WebSecurity(boolean disableDefaults, BCryptPasswordEncoder bCryptPasswordEncoder, UsersDetailsService usersDetailsService) {
        super(disableDefaults);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersDetailsService = usersDetailsService;
    }

    // All calls to the controller /user are allowed, but all other calls require authentication.

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated().and().addFilter(new JWTAuthenticationFilter(authenticationManager())).addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
