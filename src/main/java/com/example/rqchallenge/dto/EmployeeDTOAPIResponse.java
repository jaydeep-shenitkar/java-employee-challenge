package com.example.rqchallenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTOAPIResponse extends BaseEmployeeDTOAPIResponse {
	private EmployeeDTO data;
}
