package com.example.rqchallenge.util;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.model.Employee;

@Component
public class EmployeeDTOMapper implements Function<EmployeeDTO, Employee> {

	@Override
	public Employee apply(EmployeeDTO employeeDTO) {
		return Employee.builder().id(employeeDTO.getId()).employeeName(employeeDTO.getEmployeeName())
				.employeeSalary(employeeDTO.getEmployeeSalary()).employeeAge(employeeDTO.getEmployeeAge())
				.profileImage(employeeDTO.getProfileImage()).build();
	}

}
