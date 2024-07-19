package com.example.rqchallenge.util;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.model.Employee;

@Component
public class EmployeeDTOMapper implements Function<Employee, EmployeeDTO> {

	@Override
	public EmployeeDTO apply(Employee employee) {

		EmployeeDTO employeeDTO = new EmployeeDTO();
		/*employeeDTO.setId(employee.getId());
		employeeDTO.setName(employee.getName());
		employeeDTO.setAddress(employee.getAddress());
		employeeDTO.setDepartment(employee.getDepartment());*/

		return employeeDTO;

	}

}
