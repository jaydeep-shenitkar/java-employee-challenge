package com.example.rqchallenge.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.rqchallenge.dto.EmployeeDTO;

public interface IEmployeeDao {

	Optional<List<EmployeeDTO>> getAllEmployees() throws IOException;

	Optional<EmployeeDTO> getEmployeeById(String id) throws IOException;

	EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws IOException;

	String deleteEmployeeById(String id) throws IOException;

}
