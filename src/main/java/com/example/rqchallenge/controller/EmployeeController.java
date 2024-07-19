package com.example.rqchallenge.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.service.IEmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController implements IEmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	IEmployeeService employeeService;

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
		logger.info("Initiating Get all Employees API");
		List<Employee> allEmployeesList = employeeService.getAllEmployees();
		ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<List<Employee>>(allEmployeesList,
				HttpStatus.OK);
		return responseEntity;
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
		logger.info("Initiating Get Employees By Name API");
		List<Employee> searchedEmployeesList = employeeService.getEmployeesByNameSearch(searchString);
		ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<List<Employee>>(searchedEmployeesList,
				HttpStatus.OK);
		return responseEntity;

	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) {
		logger.info("Initiating Get Employees By Id API");
		Employee employee = employeeService.getEmployeeById(id);
		ResponseEntity<Employee> responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.OK);
		return responseEntity;
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
