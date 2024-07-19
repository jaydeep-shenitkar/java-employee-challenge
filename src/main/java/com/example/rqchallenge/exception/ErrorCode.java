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
	
	public static final ErrorCode FAILED_TO_PREPARE_ES_QUERY = new ErrorCode(1002,
			"Failed to prepare Elastic Search Query.");

}
