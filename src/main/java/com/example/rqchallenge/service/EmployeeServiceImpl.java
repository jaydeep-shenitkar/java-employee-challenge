package com.example.rqchallenge.service;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.rqchallenge.constatnts.Constants;
import com.example.rqchallenge.dao.IEmployeeDao;
import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.exception.EmployeeAPIException;
import com.example.rqchallenge.exception.ErrorCode;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.util.EmployeeDTOMapper;
import com.example.rqchallenge.util.EmployeeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	IEmployeeDao employeeDao;

	@Autowired
	EmployeeDTOMapper employeeDTOMapper;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EmployeeUtils employeeUtils;

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
					.orElseThrow(() -> new EmployeeAPIException(ErrorCode.NO_RECORDS_FOUND.getCode(),
							ErrorCode.NO_RECORDS_FOUND.getMessage()));

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
					.orElseThrow(() -> new EmployeeAPIException(ErrorCode.NO_MATCHING_EMPLOYEE_FOUND.getCode(),
							ErrorCode.NO_MATCHING_EMPLOYEE_FOUND.getMessage()));

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

	@Override
	public Integer getHighestSalaryOfEmployees() {
		try {

			Optional<List<EmployeeDTO>> employee = employeeDao.getAllEmployees();
			List<EmployeeDTO> employeeDTOList = employee
					.orElseThrow(() -> new EmployeeAPIException(ErrorCode.EMPTY_API_RESPONSE.getCode(),
							ErrorCode.EMPTY_API_RESPONSE.getMessage()));

			return employeeDTOList.stream().map(EmployeeDTO::getEmployeeSalary)
					.max(Comparator.comparing(Integer::intValue)).get();

		} catch (ConnectException e) {
			logger.error("Could not connect to remote host while making getAllEmployees API call ", e);
			throw new EmployeeAPIException(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
		} catch (IOException e) {
			logger.error("IOException occurred while making getAllEmployees API call", e);
			throw new EmployeeAPIException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	@Override
	public List<String> getTopNHighestEarningEmployeeNames(int number) {
		try {

			Optional<List<EmployeeDTO>> employee = employeeDao.getAllEmployees();
			List<EmployeeDTO> employeeDTOList = employee
					.orElseThrow(() -> new EmployeeAPIException(ErrorCode.EMPTY_API_RESPONSE.getCode(),
							ErrorCode.EMPTY_API_RESPONSE.getMessage()));

			return employeeDTOList.stream().sorted(Comparator.comparing(EmployeeDTO::getEmployeeSalary).reversed())
					.limit(number).map(EmployeeDTO::getEmployeeName).collect(Collectors.toList());
		} catch (ConnectException e) {
			logger.error("Could not connect to remote host while making getAllEmployees API call ", e);
			throw new EmployeeAPIException(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
		} catch (IOException e) {
			logger.error("IOException occurred while making getAllEmployees API call", e);
			throw new EmployeeAPIException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	@Override
	public Employee createEmployee(Map<String, Object> employeeInput) {

		if (employeeUtils.isEmployeeInputParamValid(employeeInput)) {
			try {
				logger.debug("Received employee details are valid");
				
				EmployeeDTO employeeDTO = new EmployeeDTO();
				employeeDTO.setEmployeeName((String) employeeInput.get(Constants.EMPLOYEE_NAME));
				employeeDTO.setEmployeeAge(((Number) employeeInput.get(Constants.EMPLOYEE_AGE)).shortValue());
				employeeDTO.setEmployeeSalary((Integer) employeeInput.get(Constants.EMPLOYEE_SALARY));

				EmployeeDTO employeeDTOResponse = employeeDao.createEmployee(employeeDTO);
				return employeeDTOMapper.apply(employeeDTOResponse);

			} catch (ConnectException e) {
				logger.error("Could not connect to remote host while making createEmployee API call ", e);
				throw new EmployeeAPIException(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
			} catch (IOException e) {
				logger.error("IOException occurred while making CreateEmployee API call", e);
				throw new EmployeeAPIException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
			}

		} else {
			logger.error("Received employee details are invalid, throwing exception");
			throw new IllegalArgumentException(Constants.INVALID_EMPLOYEE_INPUT_ERROR_MESSAGE);
		}

	}

}
