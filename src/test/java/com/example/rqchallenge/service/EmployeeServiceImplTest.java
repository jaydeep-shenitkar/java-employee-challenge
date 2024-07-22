package com.example.rqchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.rqchallenge.dao.EmployeeDaoImpl;
import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.exception.EmployeeAPIException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.util.EmployeeDTOMapper;
import com.example.rqchallenge.util.EmployeeUtils;

@SpringBootTest
public class EmployeeServiceImplTest {

	@Mock
	private EmployeeDaoImpl employeeDaoImpl;

	@Spy
	private EmployeeDTOMapper employeeDTOMapper;

	@Spy
	private EmployeeUtils employeeUtils;

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	private List<EmployeeDTO> employeeDTOList;

	private Map<String, Object> input;

	@BeforeEach
	public void init() {
		employeeDTOList = new ArrayList<>();

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(10);
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);
		employeeDTO.setProfileImage("");

		EmployeeDTO employeeDTO2 = new EmployeeDTO();
		employeeDTO2.setId(12);
		employeeDTO2.setEmployeeName("Bob");
		employeeDTO2.setEmployeeAge((short) 50);
		employeeDTO2.setEmployeeSalary(90000);
		employeeDTO2.setProfileImage("");

		EmployeeDTO employeeDTO3 = new EmployeeDTO();
		employeeDTO3.setId(14);
		employeeDTO3.setEmployeeName("Eve");
		employeeDTO3.setEmployeeAge((short) 55);
		employeeDTO3.setEmployeeSalary(50000);
		employeeDTO3.setProfileImage("");

		employeeDTOList.add(employeeDTO);
		employeeDTOList.add(employeeDTO2);
		employeeDTOList.add(employeeDTO3);

		input = new HashMap<>();

		input.put("employee_name", "John Doe");
		input.put("employee_salary", 1111);
		input.put("employee_age", 40);

	}

	@Test
	public void testGetAllEmployees() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.of(employeeDTOList));
		List<Employee> responseEmployeeList = employeeServiceImpl.getAllEmployees();

		verify(employeeDaoImpl, times(1)).getAllEmployees();
		assertEquals(3, responseEmployeeList.size());
		assertEquals("Alice", responseEmployeeList.get(0).getEmployeeName());
	}

	@Test
	public void testGetAllEmployeesEmptyList() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.empty());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getAllEmployees());
	}

	@Test
	public void testGetAllEmployeesConnectException() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenThrow(new ConnectException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getAllEmployees());
	}

	@Test
	public void testGetAllEmployeesIOException() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenThrow(new IOException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getAllEmployees());
	}

	@Test
	public void testGetEmployeesByNameSearch() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.of(employeeDTOList));

		List<Employee> responseEmployeeList = employeeServiceImpl.getEmployeesByNameSearch("Alice");

		verify(employeeDaoImpl, times(1)).getAllEmployees();
		assertEquals(1, responseEmployeeList.size());
		assertEquals("Alice", responseEmployeeList.get(0).getEmployeeName());
	}

	@Test
	public void testGetEmployeesByNameSearchEmptyList() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.empty());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getEmployeesByNameSearch("Alice"));
	}

	@Test
	public void testGetEmployeesByNameSearchListWithNoData() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.of(new ArrayList<>()));
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getEmployeesByNameSearch("Alice"));
	}

	@Test
	public void testGetEmployeesByNameSearchConnectException() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenThrow(new ConnectException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getEmployeesByNameSearch("Alice"));
	}

	@Test
	public void testGetEmployeesByNameSearchIOException() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenThrow(new IOException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getEmployeesByNameSearch("Alice"));
	}

	@Test
	public void testGetEmployeesById() throws IOException {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);

		when(employeeDaoImpl.getEmployeeById("10")).thenReturn(Optional.of(employeeDTO));

		Employee responseEmployee = employeeServiceImpl.getEmployeeById("10");

		verify(employeeDaoImpl, times(1)).getEmployeeById("10");
		assertEquals("Alice", responseEmployee.getEmployeeName());
	}

	@Test
	public void testGetEmployeesByIdEmpty() throws IOException {
		when(employeeDaoImpl.getEmployeeById("10")).thenReturn(Optional.empty());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getEmployeeById("10"));
	}

	@Test
	public void testGetEmployeesByIdConnectException() throws IOException {
		when(employeeDaoImpl.getEmployeeById("10")).thenThrow(new ConnectException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getEmployeeById("10"));
	}

	@Test
	public void testGetEmployeesByIdIOException() throws IOException {
		when(employeeDaoImpl.getEmployeeById("10")).thenThrow(new IOException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getEmployeeById("10"));
	}

	@Test
	public void testGetHighestSalaryOfEmployees() throws IOException {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);

		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.of(employeeDTOList));
		Integer response = employeeServiceImpl.getHighestSalaryOfEmployees();

		verify(employeeDaoImpl, times(1)).getAllEmployees();
		assertEquals(90000, response);
	}

	@Test
	public void testGetHighestSalaryOfEmployeesEmpty() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.empty());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getHighestSalaryOfEmployees());
	}

	@Test
	public void testGetHighestSalaryOfEmployeesConnectException() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenThrow(new ConnectException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getHighestSalaryOfEmployees());
	}

	@Test
	public void testGetHighestSalaryOfEmployeesIOException() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenThrow(new IOException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getHighestSalaryOfEmployees());
	}

	@Test
	public void testGetTopNHighestEarningEmployeeNames() throws IOException {

		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.of(employeeDTOList));
		List<String> responseList = employeeServiceImpl.getTopNHighestEarningEmployeeNames(2);

		verify(employeeDaoImpl, times(1)).getAllEmployees();
		assertEquals(2, responseList.size());
		assertEquals("Bob", responseList.get(0));
	}

	@Test
	public void testGetTopNHighestEarningEmployeeNamesEmpty() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenReturn(Optional.empty());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getTopNHighestEarningEmployeeNames(2));
	}

	@Test
	public void testGetTopNHighestEarningEmployeeNamesConnectException() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenThrow(new ConnectException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getTopNHighestEarningEmployeeNames(2));
	}

	@Test
	public void testGetTopNHighestEarningEmployeeNamesIOException() throws IOException {
		when(employeeDaoImpl.getAllEmployees()).thenThrow(new IOException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.getTopNHighestEarningEmployeeNames(2));
	}

	@Test
	public void testCreateEmployee() throws IOException {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("John Doe");
		employeeDTO.setEmployeeAge((short) 40);
		employeeDTO.setEmployeeSalary(1111);

		when(employeeDaoImpl.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);
		Employee responseEmployee = employeeServiceImpl.createEmployee(input);
		verify(employeeDaoImpl, times(1)).createEmployee(any(EmployeeDTO.class));
		assertEquals("John Doe", responseEmployee.getEmployeeName());
	}

	@Test
	public void testCreateEmployeeEmpty() throws IOException {
		assertThrows(IllegalArgumentException.class, () -> employeeServiceImpl.createEmployee(null));
	}

	@Test
	public void testCreateEmployeeConnectException() throws IOException {
		when(employeeDaoImpl.createEmployee(any(EmployeeDTO.class))).thenThrow(new ConnectException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.createEmployee(input));
	}

	@Test
	public void testCreateEmployeeIOException() throws IOException {
		when(employeeDaoImpl.createEmployee(any(EmployeeDTO.class))).thenThrow(new IOException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.createEmployee(input));
	}

	@Test
	public void testDeleteEmployeeById() throws IOException {
		when(employeeDaoImpl.deleteEmployeeById(any(String.class))).thenReturn("Successfully! Record has been deleted");
		String response = employeeServiceImpl.deleteEmployeeById("10");

		verify(employeeDaoImpl, times(1)).deleteEmployeeById("10");
		assertEquals("Successfully! Record has been deleted", response);
	}

	@Test
	public void testDeleteEmployeeByIdConnectException() throws IOException {
		when(employeeDaoImpl.deleteEmployeeById(any(String.class))).thenThrow(new ConnectException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.deleteEmployeeById("10"));
	}

	@Test
	public void testDeleteEmployeeByIdIOException() throws IOException {
		when(employeeDaoImpl.deleteEmployeeById(any(String.class))).thenThrow(new IOException());
		assertThrows(EmployeeAPIException.class, () -> employeeServiceImpl.deleteEmployeeById("10"));
	}
}
