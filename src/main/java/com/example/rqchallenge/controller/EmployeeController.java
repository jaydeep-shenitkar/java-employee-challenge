package com.example.rqchallenge.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
		logger.info("Inside Get all Employees");
		employeeService.getAllEmployees();
		logger.info("Completed Get all Employees");
		return null;
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) {
		// TODO Auto-generated method stub
		return null;
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
