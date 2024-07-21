package com.example.rqchallenge.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.rqchallenge.dto.EmployeeDTO;

/**
 * Interface for Employee Data Access Object. All the implementing classes of
 * this interface must provide their own implementation to create/delete/fetch
 * Employee either via invoking DummyEndPoints or via actually connecting to
 * some Database.
 *
 */
public interface IEmployeeDao {

	/**
	 * Fetches list of all employees
	 * 
	 * @return Optional List of Employees
	 * @throws IOException
	 */
	Optional<List<EmployeeDTO>> getAllEmployees() throws IOException;

	/**
	 * Returns matching EmployeeDTO for given Id
	 * 
	 * @param id
	 * @return Optional EmployeeDTO
	 * @throws IOException
	 */
	Optional<EmployeeDTO> getEmployeeById(String id) throws IOException;

	/**
	 * Creates an Employee and returns created Object
	 * 
	 * @param employeeDTO
	 * @return EmployeeDTO
	 * @throws IOException
	 */
	EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws IOException;

	/**
	 * Deletes employee for given Id
	 * 
	 * @param id
	 * @return message
	 * @throws IOException
	 */
	String deleteEmployeeById(String id) throws IOException;

}
