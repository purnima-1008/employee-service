package com.imaginnovate.emp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.emp.dto.EmployeeDto;
import com.imaginnovate.emp.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeDto));
	}

	@GetMapping("/{employeeId}/tax-deduction")
	public ResponseEntity<EmployeeDto> getTaxDeductionForEmployee(@PathVariable Long employeeId) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.calculateTax(employeeId));
	}
}
