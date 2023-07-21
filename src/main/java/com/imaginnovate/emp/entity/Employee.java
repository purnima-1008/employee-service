package com.imaginnovate.emp.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_employee")
public class Employee extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "employee_code")
	private String employeeCode;

	@NotNull
	@NotBlank
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@NotBlank
	@Column(name = "last_name")
	private String lastName;

	@NotNull
	@NotBlank
	@Email
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "date_of_joining")
	private LocalDate dateOfJoining;

	@NotNull
	@Column(name = "monthly_salary")
	private Double monthlySalary;

	@Size(min = 1, message = "At least one phone number must be provided.")
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EmployeePhoneNumber> employeePhoneNumberList;

}
