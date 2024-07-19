package com.example.rqchallenge.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmployeeDTO {
	int id;
	String employeeName;
	int employeeSalary;
	short employeeAge;
	String profileImage;
}
