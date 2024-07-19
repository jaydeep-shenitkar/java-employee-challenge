package com.example.rqchallenge.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeAPIResponse {
	private String status;
	private List<Employee> data;
	private String message;
}
