package com.example.rqchallenge.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeListDTOAPIResponse extends BaseEmployeeDTOAPIResponse {
	private List<EmployeeDTO> data;
}
