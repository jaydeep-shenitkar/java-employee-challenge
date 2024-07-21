package com.example.rqchallenge.exception;

/**
 * Specific Exception class to handle throttling, Too Many requests in short
 * span of time.
 *
 */
public class EmployeeAPIThrottledException extends EmployeeAPIException {

	public EmployeeAPIThrottledException(int errorCode, String message) {
		super(errorCode, message);
	}

	public EmployeeAPIThrottledException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
