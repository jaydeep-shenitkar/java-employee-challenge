package com.example.rqchallenge.exception;

/**
 * Specific exception if Employee is not present 
 *
 */
public class EmployeeNotFoundException extends EmployeeAPIException {

	public EmployeeNotFoundException(int errorCode, String message) {
		super(errorCode, message);
	}

	public EmployeeNotFoundException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
