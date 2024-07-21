package com.example.rqchallenge.util;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.rqchallenge.constatnts.Constants;

@SpringBootTest
public class EmployeeUtilsTest {

	@Autowired
	EmployeeUtils employeeUtils;

	@Test
	public void testIsParamNotNull() {
		assertTrue(employeeUtils.isParamNotNull(any(String.class), Constants.EMPLOYEE_NAME));
	}

	@Test
	public void testNullCheckIsParamNotNull() {
		assertThrows(IllegalArgumentException.class, () -> employeeUtils.isParamNotNull(null, Constants.EMPLOYEE_NAME),
				"employee_name can not be empty");
	}

	@Test
	public void testIsEmployeeAgeValid() {
		assertTrue(employeeUtils.isEmployeeAgeValid(40));
	}

	@Test
	public void testIsEmployeeAgeValidIncorrect() {
		assertFalse(() -> employeeUtils.isEmployeeAgeValid(200));
	}

	@Test
	public void testIsEmployeeSalaryValid() {
		assertTrue(employeeUtils.isEmployeeNameValid("John Doe"));
	}

	@Test
	public void testIsEmployeeSalaryValidAlphaIncorrect() {
		assertFalse(() -> employeeUtils.isEmployeeNameValid("John Doe 123"));
	}

	@Test
	public void testIsEmployeeNameValid() {
		assertTrue(employeeUtils.isEmployeeSalaryValid(45000));
	}

	@Test
	public void testIsEmployeeNameValidAlphaNumeric() {
		assertFalse(() -> employeeUtils.isEmployeeSalaryValid(-20));
	}

	@Test
	public void testIsEmployeeInputParamValidEmpty() {
		assertThrows(IllegalArgumentException.class, () -> employeeUtils.isEmployeeInputParamValid(null),
				"Employee details are not in correct format");
	}

	@Test
	public void testIsEmployeeInputParamValid() {
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("employee_name", "John Doe");
		inputMap.put("employee_age", 40);
		inputMap.put("employee_salary", 45000);
		assertTrue(employeeUtils.isEmployeeInputParamValid(inputMap));
	}

	@Test
	public void testIsEmployeeInputParamValidEmptyInputInCorrectName() {
		Map<String, Object> incorrectMap = new HashMap<>();
		incorrectMap.put("employee_name", "John Doe 123");
		incorrectMap.put("employee_age", 20);
		incorrectMap.put("employee_salary", 2000);

		assertThrows(IllegalArgumentException.class, () -> employeeUtils.isEmployeeInputParamValid(incorrectMap),
				"Employee name is not in correct format");
	}

	@Test
	public void testIsEmployeeInputParamValidEmptyInputInCorrectAge() {
		Map<String, Object> incorrectMap = new HashMap<>();
		incorrectMap.put("employee_name", "John Doe");
		incorrectMap.put("employee_age", 200);
		incorrectMap.put("employee_salary", -20);

		assertThrows(IllegalArgumentException.class, () -> employeeUtils.isEmployeeInputParamValid(incorrectMap),
				"Employee Age is not in correct format");
	}

	@Test
	public void testIsEmployeeInputParamValidInCorrectName() {
		Map<String, Object> incorrectMap = new HashMap<>();
		incorrectMap.put("employee_name", "John Doe");
		incorrectMap.put("employee_age", 40);
		incorrectMap.put("employee_salary", -20);

		assertThrows(IllegalArgumentException.class, () -> employeeUtils.isEmployeeInputParamValid(incorrectMap),
				"Employee Salary is not in correct format");
	}

}
