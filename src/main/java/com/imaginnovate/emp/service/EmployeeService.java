package com.imaginnovate.emp.service;

import com.imaginnovate.emp.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto create(EmployeeDto employeeDto);

	EmployeeDto calculateTax(Long employeeId);

}
