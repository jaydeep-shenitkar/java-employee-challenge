package com.example.rqchallenge.service;

import java.util.List;
import java.util.Map;

import com.example.rqchallenge.model.Employee;

public interface IEmployeeService {

	List<Employee> getAllEmployees();

	Employee getEmployeeById(String id);

	List<Employee> getEmployeesByNameSearch(String searchString);

	Integer getHighestSalaryOfEmployees();

	List<String> getTopNHighestEarningEmployeeNames(int number);

	Employee createEmployee(Map<String, Object> employeeInput);
}
