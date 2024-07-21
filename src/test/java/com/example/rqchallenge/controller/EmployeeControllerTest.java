package com.example.rqchallenge.controller;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.service.IEmployeeService;

@SpringBootTest
public class EmployeeControllerTest {

	@Mock
	IEmployeeService employeeService;

	@InjectMocks
	EmployeeController employeeController;

	@Test
	public void testGetAllEmployees() {
		try {
			List<Employee> asd = new ArrayList<>();
			when(employeeService.getAllEmployees()).thenReturn(new ArrayList<Employee>());
			employeeController.getAllEmployees();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
