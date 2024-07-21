package com.example.rqchallenge.util;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.model.Employee;

/**
 * Function class to convert incoming EmployeeDTO to response Employee.
 * 
 * This is particularly useful when we have multiple fields associated with
 * Employee class in Database viz. isActive, password etc and we don't want to
 * expose these fields to API consuming end point. In such cases we can use
 * common Mapper functions to convert outgoing employee object to consuming end
 * point and vice versa.
 * 
 */
@Component
public class EmployeeDTOMapper implements Function<EmployeeDTO, Employee> {

	@Override
	public Employee apply(EmployeeDTO employeeDTO) {
		return Employee.builder().id(employeeDTO.getId()).employeeName(employeeDTO.getEmployeeName())
				.employeeSalary(employeeDTO.getEmployeeSalary()).employeeAge(employeeDTO.getEmployeeAge())
				.profileImage(employeeDTO.getProfileImage()).build();
	}

}
