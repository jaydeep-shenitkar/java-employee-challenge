package com.example.rqchallenge.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Employee {
	private int id;
	private String employeeName;
	private int employeeSalary;
	private short employeeAge;
	private String profileImage;
}
