package com.example.rqchallenge.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rqchallenge.dao.IEmployeeDao;
import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.util.EmployeeDTOMapper;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	IEmployeeDao employeeDao;
	
	@Autowired
	EmployeeDTOMapper employeeDTOMapper;
	
	@Override
	public List<Employee> getAllEmployees() {
		logger.info("EmployeeServiceImpl GET ALL Employees");
		Optional<List<Employee>> employee = employeeDao.getAllEmployees();
//		List<EmployeeDTO> employee = 
		
		List<Employee> empList = employee.get();
		
		List<String> collect = empList.stream().map(Employee::getEmployeeName).collect(Collectors.toList());
		
		List<EmployeeDTO> collect2 = empList.stream().map(employeeDTOMapper).collect(Collectors.toList());
		
		employee.ifPresent(list -> list.stream()
	            .map(Employee::getEmployeeName).collect(Collectors.toList()));


		return null;
	}

}
