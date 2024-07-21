package com.example.rqchallenge.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.service.IEmployeeService;

/**
 * Implementation of IEmployeeController, provides and end point to invoke all
 * employee related APIs
 *
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController implements IEmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	IEmployeeService employeeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.rqchallenge.controller.IEmployeeController#getAllEmployees()
	 */
	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
		logger.info("Initiating Get all Employees API");
		List<Employee> allEmployeesList = employeeService.getAllEmployees();
		ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<List<Employee>>(allEmployeesList,
				HttpStatus.OK);
		return responseEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.rqchallenge.controller.IEmployeeController#
	 * getEmployeesByNameSearch(java.lang.String)
	 */
	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
		logger.info("Initiating Get Employees By Name API");
		List<Employee> searchedEmployeesList = employeeService.getEmployeesByNameSearch(searchString);
		ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<List<Employee>>(searchedEmployeesList,
				HttpStatus.OK);
		return responseEntity;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.rqchallenge.controller.IEmployeeController#getEmployeeById(java.
	 * lang.String)
	 */
	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) {
		logger.info("Initiating Get Employees By Id API");
		Employee employee = employeeService.getEmployeeById(id);
		ResponseEntity<Employee> responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.OK);
		return responseEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.rqchallenge.controller.IEmployeeController#
	 * getHighestSalaryOfEmployees()
	 */
	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		logger.info("Initiating Get Highest salary of employees");
		Integer highestSalary = employeeService.getHighestSalaryOfEmployees();
		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(highestSalary, HttpStatus.OK);
		return responseEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.rqchallenge.controller.IEmployeeController#
	 * getTopTenHighestEarningEmployeeNames()
	 */
	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
		logger.info("Initiating fetching of Top 10 Highest earning employee names");
		List<String> topTenHighestEarningEmpNames = employeeService.getTopNHighestEarningEmployeeNames(10);
		ResponseEntity<List<String>> responseEntity = new ResponseEntity<List<String>>(topTenHighestEarningEmpNames,
				HttpStatus.OK);
		return responseEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.rqchallenge.controller.IEmployeeController#createEmployee(java.
	 * util.Map)
	 */
	@Override
	public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
		logger.info("Creating new Employee from received input");
		Employee createdEmployee = employeeService.createEmployee(employeeInput);
		ResponseEntity<Employee> responseEntity = new ResponseEntity<Employee>(createdEmployee, HttpStatus.CREATED);
		return responseEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.rqchallenge.controller.IEmployeeController#deleteEmployeeById(
	 * java.lang.String)
	 */
	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) {
		logger.info("Deleting Employee with Id " + id);
		String message = employeeService.deleteEmployeeById(id);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
		return responseEntity;
	}

}
