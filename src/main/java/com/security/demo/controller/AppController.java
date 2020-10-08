package com.security.demo.controller;

import com.security.demo.model.Employee;
import com.security.demo.model.User;
import com.security.demo.repository.EmployeesRepository;
import com.security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class AppController {

    private UserRepository userRepository;
    private EmployeesRepository employeesRepository;

    public AppController(UserRepository userRepository, EmployeesRepository employeesRepository) {
        this.userRepository = userRepository;
        this.employeesRepository = employeesRepository;
    }

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/employees")
    public List<Employee> getAllEmployees() {
        return employeesRepository.findAll();
    }
}
