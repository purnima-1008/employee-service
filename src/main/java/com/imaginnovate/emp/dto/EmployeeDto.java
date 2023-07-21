package com.imaginnovate.emp.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

	private Long id;

	private String employeeCode;

	private String firstName;

	private String lastName;

	private String email;

	private LocalDate dateOfJoining;

	private Double monthlySalary;
	
	private Double yearlySalary;

	private List<EmployeePhoneNumberDto> employeePhoneNumberList;

	private Double taxAmount;

	private Double cessAmount;

}
