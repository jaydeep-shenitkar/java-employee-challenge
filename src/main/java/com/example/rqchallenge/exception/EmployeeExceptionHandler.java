package com.example.rqchallenge.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeExceptionHandler.class);

	@ExceptionHandler(EmployeeAPIThrottledException.class)
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	public ResponseEntity<String> handleThrottlingException(EmployeeAPIThrottledException ex) {
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
	}

	@ExceptionHandler(EmployeeAPIException.class)
	public ResponseEntity<APIErrorResponse> handleEmployeeAPiException(EmployeeAPIException ex) {
		APIErrorResponse res = new APIErrorResponse();
		res.setTimeStamp(new Date().getTime());
		res.setErrorCode(ex.getErrorCode());
		res.setMessage(ex.getMessage());
		return ResponseEntity.status(ex.getErrorCode()).body(res);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIErrorResponse> handleException(Exception ex) {
		logger.error("Exception occurred while processing API request ", ex);
		APIErrorResponse res = new APIErrorResponse();
		res.setTimeStamp(new Date().getTime());
		res.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		res.setMessage(ex.getMessage());
		return ResponseEntity.internalServerError().body(res);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<APIErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		APIErrorResponse res = new APIErrorResponse();
		res.setErrorCode(HttpStatus.BAD_REQUEST.value());
		res.setTimeStamp(new Date().getTime());
		res.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

}
