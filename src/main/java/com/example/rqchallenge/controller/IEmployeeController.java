package com.example.rqchallenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.rqchallenge.model.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Employee Controller interface expects implementing classes to provide
 * implementation for for all APIs
 *
 */
@RestController
public interface IEmployeeController {

	/**
	 * API to fetch list of All Employees
	 * 
	 * @return List of All Employees
	 * @throws IOException
	 */
	@GetMapping("/getAllEmployees")
	ResponseEntity<List<Employee>> getAllEmployees() throws IOException;

	/**
	 * Searches all employees with their name containing search String
	 * 
	 * @param searchString
	 * @return List of employees satisfying search criteria
	 */
	@GetMapping("/search/{searchString}")
	ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString);

	/**
	 * Returns employee whoes EmployeeID matches with given employeeID
	 * 
	 * @param id
	 * @return Employee
	 */
	@GetMapping("/{id}")
	ResponseEntity<Employee> getEmployeeById(@PathVariable String id);

	/**
	 * Finds highest salary amongst all employees
	 * 
	 * @return Highest Salary
	 */
	@GetMapping("/highestSalary")
	ResponseEntity<Integer> getHighestSalaryOfEmployees();

	/**
	 * Finds top ten employees with highest salaries
	 * 
	 * @return List of Employee Names
	 */
	@GetMapping("/topTenHighestEarningEmployeeNames")
	ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

	/**
	 * Creates Employee with input
	 * 
	 * @param
	 * @return Employee object created
	 */
	@PostMapping("/createEmployee")
	ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput);

	/**
	 * Deletes employee associated with given employee Id
	 * 
	 * @param id
	 * @return message
	 */
	@DeleteMapping("/{id}")
	ResponseEntity<String> deleteEmployeeById(@PathVariable String id);

}
