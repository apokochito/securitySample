package com.security.demo.repository;

import com.security.demo.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends MongoRepository<Employee, String> {
    Employee findEmployeeBy_id(String id);
}
