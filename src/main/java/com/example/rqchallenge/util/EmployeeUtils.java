package com.example.rqchallenge.util;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.rqchallenge.constatnts.Constants;

@Component
public class EmployeeUtils {

	public boolean isParamNotNull(Object obj, String param) {
		if (Objects.isNull(obj)) {
			throw new IllegalArgumentException(String.format(Constants.INVALID_INPUT_ERROR_MESSAGE, param));
		}
		return true;
	}

	public boolean isEmployeeNameValid(Object obj) {
		if (isParamNotNull(obj, Constants.EMPLOYEE_NAME)) {
			String name = (String) obj;
			Pattern p = Pattern.compile(Constants.EMPLOYEE_NAME_REGEX);
			Matcher m = p.matcher(name);
			return m.matches();
		}
		return false;
	}

	public boolean isEmployeeAgeValid(Object obj) {
		if (isParamNotNull(obj, Constants.EMPLOYEE_NAME)) {
			Integer age = (Integer) obj;
			if (age > 17 && age < 120) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean isEmployeeSalaryValid(Object obj) {
		if (isParamNotNull(obj, Constants.EMPLOYEE_NAME)) {
			Integer salary = (Integer) obj;
			if (salary > 0) {
				return true;
			}
			return false;
		}
		return false;

	}

	public boolean isEmployeeInputParamValid(Map<String, Object> employeeInput) {

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
