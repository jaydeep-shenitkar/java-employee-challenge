package com.example.rqchallenge.util;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import com.example.rqchallenge.constatnts.Constants;

/**
 * Common Utility class contains utility methods
 */
@Component
public class EmployeeUtils {

	/**
	 * @param obj
	 * @param param
	 * @return if input object is null or not
	 */
	public boolean isParamNotNull(Object obj, String param) {
		if (Objects.isNull(obj)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if input EmployeeName is in correct format or not
	 * 
	 * @param obj
	 * @return boolean
	 */
	public boolean isEmployeeNameValid(Object obj) {
		if (isParamNotNull(obj, Constants.EMPLOYEE_NAME)) {
			String name = (String) obj;
			Pattern p = Pattern.compile(Constants.EMPLOYEE_NAME_REGEX);
			Matcher m = p.matcher(name);
			return m.matches();
		}
		return false;
	}

	/**
	 * Checks if input Employee Age is valid or not
	 * 
	 * @param obj
	 * @return boolean
	 */
	public boolean isEmployeeAgeValid(Object obj) {
		if (isParamNotNull(obj, Constants.EMPLOYEE_AGE)) {
			Integer age = (Integer) obj;
			if (age > 17 && age < 120) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Checks if input Employee Salary is valid or not
	 * @param obj
	 * @return boolean
	 */
	public boolean isEmployeeSalaryValid(Object obj) {
		if (isParamNotNull(obj, Constants.EMPLOYEE_SALARY)) {
			Integer salary = (Integer) obj;
			if (salary > 0) {
				return true;
			}
			return false;
		}
		return false;

	}

	/**
	 * Checks createEmployee input Map is valid or not
	 *  
	 * @param employeeInput
	 * @return boolean
	 */
	public boolean isEmployeeInputParamValid(Map<String, Object> employeeInput) {

		if (MapUtils.isEmpty(employeeInput)) {
			throw new IllegalArgumentException(Constants.INVALID_EMPLOYEE_INPUT_ERROR_MESSAGE);
		}

		boolean isNameValid = isEmployeeNameValid(employeeInput.get(Constants.EMPLOYEE_NAME));
		if (!isNameValid) {
			throw new IllegalArgumentException(Constants.INVALID_EMPLOYEE_NAME_ERROR_MESSAGE);
		}

		boolean isAgeValid = isEmployeeAgeValid(employeeInput.get(Constants.EMPLOYEE_AGE));
		if (!isAgeValid) {
			throw new IllegalArgumentException(Constants.INVALID_EMPLOYEE_AGE_ERROR_MESSAGE);
		}

		boolean isSalaryValid = isEmployeeSalaryValid(employeeInput.get(Constants.EMPLOYEE_SALARY));
		if (!isSalaryValid) {
			throw new IllegalArgumentException(Constants.INVALID_EMPLOYEE_SALARY_ERROR_MESSAGE);
		}
		return true;
	}

}
