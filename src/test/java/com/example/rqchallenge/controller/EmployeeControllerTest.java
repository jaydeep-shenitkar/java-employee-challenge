package com.example.rqchallenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.service.IEmployeeService;

@SpringBootTest
public class EmployeeControllerTest {

	private static final String SEARCH_STRING = "Peter";
	private static final String SEARCH_BY_EMP_ID = "10";
	@Mock
	IEmployeeService employeeService;

	@InjectMocks
	EmployeeController employeeController;

	private List<Employee> employeeList;

	private List<String> employeeNameList;

	@BeforeEach
	public void init() {
		employeeList = new ArrayList<>();
		Employee employee = Employee.builder().id(10).employeeName("Peter Parker").employeeSalary(10000)
				.employeeAge((short) 45).profileImage("").build();

		Employee employee2 = Employee.builder().id(12).employeeName("Peter Pan").employeeSalary(15000)
				.employeeAge((short) 50).profileImage("").build();

		employeeList.add(employee);
		employeeList.add(employee2);

		employeeNameList = new ArrayList<>();

		employeeNameList.add("Peter Parker");
		employeeNameList.add("Peter Pan");
		employeeNameList.add("Frodo Baggins");
		employeeNameList.add("Bilbo Baggins");
		employeeNameList.add("Samwise Gamgee");
		employeeNameList.add("Ron Weasley");
		employeeNameList.add("Ginny Weasley");
		employeeNameList.add("Neville Longbottom");
		employeeNameList.add("Draco Malfoy");
		employeeNameList.add("Rubeus Hagrid");

	}

	@Test
	public void testGetAllEmployees() throws IOException {
		when(employeeService.getAllEmployees()).thenReturn(employeeList);

		ResponseEntity<List<Employee>> responseEmpoyeeList = employeeController.getAllEmployees();

		verify(employeeService, times(1)).getAllEmployees();
		assertEquals(2, responseEmpoyeeList.getBody().size());
		assertEquals("Peter Parker", responseEmpoyeeList.getBody().get(0).getEmployeeName());
	}

	@Test
	public void testGetEmployeesByNameSearch() throws IOException {
		when(employeeService.getEmployeesByNameSearch(SEARCH_STRING)).thenReturn(employeeList);

		ResponseEntity<List<Employee>> responseEmpoyeeList = employeeController.getEmployeesByNameSearch(SEARCH_STRING);

		verify(employeeService, times(1)).getEmployeesByNameSearch(SEARCH_STRING);
		assertEquals(2, responseEmpoyeeList.getBody().size());
		assertEquals("Peter Parker", responseEmpoyeeList.getBody().get(0).getEmployeeName());
	}

	@Test
	public void testGetEmployeeById() throws IOException {
		Employee employee = Employee.builder().id(10).employeeName("Peter Parker").employeeSalary(10000)
				.employeeAge((short) 45).profileImage("").build();
		when(employeeService.getEmployeeById(SEARCH_BY_EMP_ID)).thenReturn(employee);

		ResponseEntity<Employee> responseEmpoyee = employeeController.getEmployeeById(SEARCH_BY_EMP_ID);

		verify(employeeService, times(1)).getEmployeeById(SEARCH_BY_EMP_ID);
		assertEquals("Peter Parker", responseEmpoyee.getBody().getEmployeeName());
	}

	@Test
	public void testGetHighestSalaryOfEmployees() throws IOException {
		when(employeeService.getHighestSalaryOfEmployees()).thenReturn(15000);

		ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

		verify(employeeService, times(1)).getHighestSalaryOfEmployees();
		assertEquals(15000, response.getBody());
	}

	@Test
	public void testGetTopTenHighestEarningEmployeeNames() throws IOException {
		when(employeeService.getTopNHighestEarningEmployeeNames(10)).thenReturn(employeeNameList);

		ResponseEntity<List<String>> responseEmployeeNameList = employeeController
				.getTopTenHighestEarningEmployeeNames();
		verify(employeeService, times(1)).getTopNHighestEarningEmployeeNames(10);

		assertEquals(10, responseEmployeeNameList.getBody().size());
	}

	@Test
	public void testCreateEmployee() throws IOException {
		Map<String, Object> input = new HashMap<>();

		input.put("employee_name", "John Doe");
		input.put("employee_salary", 1111);
		input.put("employee_age", (short) 40);

		Employee employee = Employee.builder().id(10).employeeName("John Doe").employeeSalary(1111)
				.employeeAge((short) 40).profileImage("").build();

		when(employeeService.createEmployee(input)).thenReturn(employee);

		ResponseEntity<Employee> responseEmployee = employeeController.createEmployee(input);
		verify(employeeService, times(1)).createEmployee(input);

		assertEquals(input.get("employee_name"), responseEmployee.getBody().getEmployeeName());
	}

	@Test
	public void testDeleteEmployee() throws IOException {
		String message = "Successfully deleted record";
		when(employeeService.deleteEmployeeById("10")).thenReturn(message);

		ResponseEntity<String> response = employeeController.deleteEmployeeById("10");
		verify(employeeService, times(1)).deleteEmployeeById("10");

		assertEquals(message, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
