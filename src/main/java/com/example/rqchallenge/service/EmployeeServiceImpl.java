package com.example.rqchallenge.service;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.rqchallenge.dao.IEmployeeDao;
import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.exception.EmployeeAPIException;
import com.example.rqchallenge.exception.ErrorCode;
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
		try {

			Optional<List<EmployeeDTO>> employee = employeeDao.getAllEmployees();
			List<EmployeeDTO> employeeDTOList = employee
					.orElseThrow(() -> new EmployeeAPIException(ErrorCode.EMPTY_API_RESPONSE.getCode(),
							ErrorCode.EMPTY_API_RESPONSE.getMessage()));

			List<Employee> employeeList = employeeDTOList.stream().map(employeeDTOMapper).collect(Collectors.toList());
			return employeeList;

		} catch (ConnectException e) {
			logger.error("Could not connect to remote host while making getAllEmployees API call ", e);
			throw new EmployeeAPIException(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
		} catch (IOException e) {
			logger.error("IOException occurred while making getAllEmployees API call", e);
			throw new EmployeeAPIException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	@Override
	public List<Employee> getEmployeesByNameSearch(String searchString) {
		try {

			Optional<List<EmployeeDTO>> employee = employeeDao.getAllEmployees();
			List<EmployeeDTO> employeeDTOList = employee
					.orElseThrow(() -> new EmployeeAPIException(ErrorCode.EMPTY_API_RESPONSE.getCode(),
							ErrorCode.EMPTY_API_RESPONSE.getMessage()));

			List<Employee> employeeList = employeeDTOList.stream()
					.filter(emp -> StringUtils.containsIgnoreCase(emp.getEmployeeName(), searchString))
					.map(employeeDTOMapper).collect(Collectors.toList());
			return employeeList;

		} catch (ConnectException e) {
			logger.error("Could not connect to remote host while making getEmployeesByNameSearch API call ", e);
			throw new EmployeeAPIException(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
		} catch (IOException e) {
			logger.error("IOException occurred while making getEmployeesByNameSearch API call", e);
			throw new EmployeeAPIException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	@Override
	public Employee getEmployeeById(String id) {
		try {
			Optional<EmployeeDTO> employeeOptional = employeeDao.getEmployeeById(id);
			EmployeeDTO employeeDTO = employeeOptional
					.orElseThrow(() -> new EmployeeAPIException(ErrorCode.EMPTY_API_RESPONSE.getCode(),
							ErrorCode.EMPTY_API_RESPONSE.getMessage()));
			
			Employee employee = employeeDTOMapper.apply(employeeDTO);
			return employee;

		} catch (ConnectException e) {
			logger.error("Could not connect to remote host while making getAllEmployees API call ", e);
			throw new EmployeeAPIException(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
		} catch (IOException e) {
			logger.error("IOException occurred while making getAllEmployees API call", e);
			throw new EmployeeAPIException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

}
