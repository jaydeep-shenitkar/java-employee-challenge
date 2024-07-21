package com.example.rqchallenge.exception;

/**
 * Generic Exception class while handling Employee related API calls.
 *
 */
public class EmployeeAPIException extends RuntimeException {

	private int errorCode;
	private String message;

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	public EmployeeAPIException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public EmployeeAPIException(int errorCode, String message, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.message = message;

	}

}
