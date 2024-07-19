package com.example.rqchallenge.exception;

public class EmployeeNotFoundException extends EmployeeAPIException {

	public EmployeeNotFoundException(int errorCode, String message) {
		super(errorCode, message);
	}

	public EmployeeNotFoundException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
