package com.example.rqchallenge.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIErrorResponse {
	private int errorCode;
	private String message;
	private long timeStamp;
}
