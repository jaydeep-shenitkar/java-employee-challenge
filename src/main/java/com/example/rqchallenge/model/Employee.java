package com.example.rqchallenge.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Employee {
	int id;
	String employeeName;
	int employeeSalary;
	short employeeAge;
	String profileImage;
	
}
