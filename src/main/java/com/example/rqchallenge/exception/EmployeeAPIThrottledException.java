package com.example.rqchallenge.exception;

public class EmployeeAPIThrottledException extends EmployeeAPIException {

	public EmployeeAPIThrottledException(int errorCode, String message) {
		super(errorCode, message);
	}

	public EmployeeAPIThrottledException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
