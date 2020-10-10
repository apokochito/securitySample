package com.security.demo.controller;

import com.security.demo.model.Employee;
import com.security.demo.model.User;
import com.security.demo.repository.EmployeesRepository;
import com.security.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/resources")
public class AppController {

    private UserRepository userRepository;
    private EmployeesRepository employeesRepository;

    public AppController(UserRepository userRepository, EmployeesRepository employeesRepository) {
        this.userRepository = userRepository;
        this.employeesRepository = employeesRepository;
    }

    // Protected resources
    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        log.info("Controller - Returning users...");
        return userRepository.findAll();
    }

    @GetMapping(value = "/employees")
    public List<Employee> getAllEmployees() {
        log.info("Controller - Returning employees...");
        return employeesRepository.findAll();
    }
}
