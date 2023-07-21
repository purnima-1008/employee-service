package com.imaginnovate.emp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.imaginnovate.emp.dto.EmployeeDto;
import com.imaginnovate.emp.dto.EmployeePhoneNumberDto;
import com.imaginnovate.emp.entity.Employee;
import com.imaginnovate.emp.entity.EmployeePhoneNumber;
import com.imaginnovate.emp.repository.EmployeeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepo employeeRepo;

	private static final BigDecimal SLAB1 = new BigDecimal("250000");
	private static final BigDecimal SLAB2 = new BigDecimal("500000");
	private static final BigDecimal SLAB3 = new BigDecimal("1000000");
	private static final BigDecimal CESS_THRESHOLD = new BigDecimal("2500000");
	private static final BigDecimal CESS_RATE = new BigDecimal("0.02");

	@Override
	public EmployeeDto create(EmployeeDto employeeDto) {
		Employee employee = Employee.builder().build();
		try {
			BeanUtils.copyProperties(employeeDto, employee);
			employee = employee.toBuilder().createdDate(LocalDateTime.now()).build();
			linkPhoneNumbers(employeeDto, employee);
			BeanUtils.copyProperties(employeeRepo.save(employee), employeeDto);
			employeeDto.setEmployeePhoneNumberList(PhoneEntityToDto(employee));
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return employeeDto;
	}

	private List<EmployeePhoneNumberDto> PhoneEntityToDto(Employee employee) {
		return employee.getEmployeePhoneNumberList().stream().map(i -> {
			EmployeePhoneNumberDto dto = new EmployeePhoneNumberDto();
			BeanUtils.copyProperties(i, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	private void linkPhoneNumbers(EmployeeDto employeeDto, Employee employee) {
		List<EmployeePhoneNumber> collect = employeeDto.getEmployeePhoneNumberList().stream().map(phone -> {
			EmployeePhoneNumber phoneNumber = new EmployeePhoneNumber();
			System.out.println(phone.getPhoneNumber());
			phoneNumber.setPhoneNumber(phone.getPhoneNumber());
			phoneNumber.setEmployee(employee);
			return phoneNumber;
		}).collect(Collectors.toList());
		employee.setEmployeePhoneNumberList(collect);
	}

	@Override
	public EmployeeDto calculateTax(Long employeeId) {
		Employee employee = employeeRepo.findById(employeeId).orElseThrow();
		int currentYear = LocalDate.now().getYear();

		LocalDate joiningDate = employee.getDateOfJoining();
		int joiningYear = joiningDate.getYear();

		int monthsWorked = (joiningYear == currentYear) ? LocalDate.now().getMonthValue() - joiningDate.getMonthValue() + 1 : 12 - joiningDate.getMonthValue() + 1;

		BigDecimal yearlySalary = BigDecimal.valueOf(employee.getMonthlySalary()*12).multiply(BigDecimal.valueOf(monthsWorked));

		BigDecimal taxAmount = calculateTax(yearlySalary);
		BigDecimal cessAmount = calculateCess(yearlySalary);
		return EmployeeDto.builder().id(employee.getId()).firstName(employee.getFirstName()).lastName(employee.getLastName())
				.yearlySalary(yearlySalary.doubleValue())
				.taxAmount(taxAmount.doubleValue()).cessAmount(cessAmount.doubleValue()).build();
	}

	public static BigDecimal calculateTax(BigDecimal yearlySalary) {
		BigDecimal tax = BigDecimal.ZERO;

		if (yearlySalary.compareTo(SLAB1) > 0) {
			BigDecimal taxableAmount = yearlySalary.min(SLAB2).subtract(SLAB1);
			tax = tax.add(taxableAmount.multiply(new BigDecimal("0.05")));
		}

		if (yearlySalary.compareTo(SLAB2) > 0) {
			BigDecimal taxableAmount = yearlySalary.min(SLAB3).subtract(SLAB2);
			tax = tax.add(taxableAmount.multiply(new BigDecimal("0.10")));
		}

		if (yearlySalary.compareTo(SLAB3) > 0) {
			BigDecimal taxableAmount = yearlySalary.subtract(SLAB3);
			tax = tax.add(taxableAmount.multiply(new BigDecimal("0.20")));
		}

		return tax;
	}

	public static BigDecimal calculateCess(BigDecimal yearlySalary) {
		if (yearlySalary.compareTo(CESS_THRESHOLD) > 0) {
			BigDecimal taxableAmount = yearlySalary.subtract(CESS_THRESHOLD);
			return taxableAmount.multiply(CESS_RATE).setScale(2, RoundingMode.HALF_UP);
		}
		return BigDecimal.ZERO;
	}

}
