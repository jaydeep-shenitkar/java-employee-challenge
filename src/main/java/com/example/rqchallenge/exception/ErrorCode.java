package com.example.rqchallenge.exception;

public class ErrorCode {

	private final int code;
	private final String message;

	private ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	// Define static final instances for commonly used error codes
	public static final ErrorCode TOO_MANY_REQUESTS = new ErrorCode(429, "Too Many Requests. Please retry again later");

	public static final ErrorCode EMPTY_API_RESPONSE = new ErrorCode(404, "No Response received from API");
	
	public static final ErrorCode NO_MATCHING_EMPLOYEE_FOUND = new ErrorCode(404, "No matching Employee Found for given employee Id");
	public static final ErrorCode NO_RECORDS_FOUND = new ErrorCode(404, "No matching employees found for given search string");
	
	

}
