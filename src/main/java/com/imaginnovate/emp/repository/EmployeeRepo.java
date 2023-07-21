package com.imaginnovate.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imaginnovate.emp.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
