package com.example.rqchallenge.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.dto.EmployeeDTOAPIResponse;
import com.example.rqchallenge.dto.EmployeeListDTOAPIResponse;
import com.example.rqchallenge.exception.EmployeeAPIException;
import com.example.rqchallenge.exception.EmployeeAPIThrottledException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class EmployeeDaoImplTest {

	@Mock
	private CloseableHttpClient mockHttpClient;

	@Mock
	CloseableHttpResponse mockResponse;

	@Mock
	StatusLine statusLine;

	@Mock
	ObjectMapper mockObjectMapper;

	@Mock
	HttpEntity mockHttpEntity;

	@InjectMocks
	EmployeeDaoImpl employeeDaoImpl;

	@BeforeEach
	public void setUp() throws ClientProtocolException, IOException {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAllEmployees() throws IOException {

		String str = null;
		when(statusLine.getStatusCode()).thenReturn(200);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

		EmployeeListDTOAPIResponse emListDTOAPIResponse = new EmployeeListDTOAPIResponse();
		List<EmployeeDTO> employeeList = new ArrayList<>();
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);

		EmployeeDTO employeeDTO2 = new EmployeeDTO();
		employeeDTO2.setEmployeeName("Bob");
		employeeDTO2.setEmployeeAge((short) 50);
		employeeDTO2.setEmployeeSalary(90000);

		employeeList.add(employeeDTO);
		employeeList.add(employeeDTO2);

		emListDTOAPIResponse.setData(employeeList);

		when(mockObjectMapper.readValue(eq(str), eq(EmployeeListDTOAPIResponse.class)))
				.thenReturn(emListDTOAPIResponse);

		Optional<List<EmployeeDTO>> empDtOListResponse = employeeDaoImpl.getAllEmployees();

		assertTrue(empDtOListResponse.isPresent());
		assertEquals(empDtOListResponse.get().size(), 2);
		assertEquals(empDtOListResponse.get().get(0).getEmployeeName(), "Alice");
	}

	@Test
	public void testGetAllEmployeesTooManyRequests() throws IOException {

		String str = null;
		when(statusLine.getStatusCode()).thenReturn(429);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

		assertThrows(EmployeeAPIThrottledException.class, () -> employeeDaoImpl.getAllEmployees());
	}

	@Test
	public void testGetAllEmployeesAnyOtherException() throws IOException {

		String str = null;
		when(statusLine.getStatusCode()).thenReturn(500);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

		assertThrows(EmployeeAPIException.class, () -> employeeDaoImpl.getAllEmployees());
	}

	@Test
	public void testGetEmployeeById() throws IOException {

		String str = null;
		when(statusLine.getStatusCode()).thenReturn(200);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);

		EmployeeDTOAPIResponse employeeDTOAPIResponse = new EmployeeDTOAPIResponse();
		employeeDTOAPIResponse.setStatus("Success");
		employeeDTOAPIResponse.setData(employeeDTO);
		employeeDTOAPIResponse.setMessage("Successfully fetched data");

		when(mockObjectMapper.readValue(eq(str), eq(EmployeeDTOAPIResponse.class))).thenReturn(employeeDTOAPIResponse);

		Optional<EmployeeDTO> empDtOListResponse = employeeDaoImpl.getEmployeeById("20");

		assertTrue(empDtOListResponse.isPresent());
		assertEquals(empDtOListResponse.get().getEmployeeName(), "Alice");
	}

	@Test
	public void testGetEmployeeByIdTooManyRequests() throws IOException {

		when(statusLine.getStatusCode()).thenReturn(429);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

		assertThrows(EmployeeAPIThrottledException.class, () -> employeeDaoImpl.getEmployeeById("20"));
	}

	@Test
	public void testGetEmployeeByIdAnyOtherException() throws IOException {

		when(statusLine.getStatusCode()).thenReturn(500);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

		assertThrows(EmployeeAPIException.class, () -> employeeDaoImpl.getEmployeeById("20"));
	}

	@Test
	public void testcreateEmployee() throws IOException {

		String str = null;
		when(statusLine.getStatusCode()).thenReturn(200);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);

		EmployeeDTOAPIResponse employeeDTOAPIResponse = new EmployeeDTOAPIResponse();
		employeeDTOAPIResponse.setStatus("Success");
		employeeDTOAPIResponse.setData(employeeDTO);
		employeeDTOAPIResponse.setMessage("Successfully created data");

		when(mockObjectMapper.readValue(eq(str), eq(EmployeeDTOAPIResponse.class))).thenReturn(employeeDTOAPIResponse);

		EmployeeDTO empDtOListResponse = employeeDaoImpl.createEmployee(employeeDTO);
		assertEquals(empDtOListResponse.getEmployeeName(), "Alice");
	}

	@Test
	public void testCreateEmployeeTooManyRequests() throws IOException {

		when(statusLine.getStatusCode()).thenReturn(429);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);

		assertThrows(EmployeeAPIThrottledException.class, () -> employeeDaoImpl.createEmployee(employeeDTO));
	}

	@Test
	public void testCreateEmployeeAnyOtherException() throws IOException {

		when(statusLine.getStatusCode()).thenReturn(500);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);

		assertThrows(EmployeeAPIException.class, () -> employeeDaoImpl.createEmployee(employeeDTO));
	}

	@Test
	public void testDeleteEmployeeById() throws IOException {

		String str = null;
		when(statusLine.getStatusCode()).thenReturn(200);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpDelete.class))).thenReturn(mockResponse);

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeName("Alice");
		employeeDTO.setEmployeeAge((short) 45);
		employeeDTO.setEmployeeSalary(10000);

		EmployeeDTOAPIResponse employeeDTOAPIResponse = new EmployeeDTOAPIResponse();
		employeeDTOAPIResponse.setStatus("Success");
		employeeDTOAPIResponse.setData(employeeDTO);
		employeeDTOAPIResponse.setMessage("Successfully created data");
		TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
		};

		HashMap<String, String> apiResponse = new HashMap<>();
		apiResponse.put("status", "success");
		apiResponse.put("data", "20");
		apiResponse.put("message", "Successfully! Record has been deleted");

		when(mockObjectMapper.readValue(eq(str), any(TypeReference.class))).thenReturn(apiResponse);

		String message = employeeDaoImpl.deleteEmployeeById("20");
		assertEquals(message, "Successfully! Record has been deleted");
	}

	@Test
	public void testDeleteEmployeeByIdTooManyRequests() throws IOException {

		when(statusLine.getStatusCode()).thenReturn(429);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpDelete.class))).thenReturn(mockResponse);

		assertThrows(EmployeeAPIThrottledException.class, () -> employeeDaoImpl.deleteEmployeeById("20"));
	}

	@Test
	public void testDeleteEmployeeByIdAnyOtherException() throws IOException {

		when(statusLine.getStatusCode()).thenReturn(500);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);
		when(mockResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockHttpClient.execute(any(HttpDelete.class))).thenReturn(mockResponse);

		assertThrows(EmployeeAPIException.class, () -> employeeDaoImpl.deleteEmployeeById("20"));
	}

}
