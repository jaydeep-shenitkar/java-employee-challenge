package com.example.rqchallenge.dao;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.example.rqchallenge.constants.Constants;
import com.example.rqchallenge.dto.EmployeeDTO;
import com.example.rqchallenge.dto.EmployeeDTOAPIResponse;
import com.example.rqchallenge.dto.EmployeeListDTOAPIResponse;
import com.example.rqchallenge.exception.EmployeeAPIException;
import com.example.rqchallenge.exception.EmployeeAPIThrottledException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DummyAPI based implementation of <code>IEmployeeDao</code>. Invokes various
 * APIs provided by Dummy end point to create/delete/fetch Employees
 *
 */
@Component
@EnableRetry
public class EmployeeDaoImpl implements IEmployeeDao {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 
	 * Invokes dummy API to get list of all Employees.
	 * Retries API if there is any issue in connection. 
	 * @throws EmployeeAPIThrottledException In case too many requests are sent within short span of time
	 * @throws EmployeeAPIException If there is any other exceptional condition
	 * 
	 * @see com.example.rqchallenge.dao.IEmployeeDao#getAllEmployees()
	 * 
	 */
	@Override
	@Retryable(retryFor = ConnectException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
	public Optional<List<EmployeeDTO>> getAllEmployees() throws IOException {

		logger.debug("Invoking API: " + RestAPIURLs.GET_ALL_EMPLOYEES);

		HttpGet httpGet = new HttpGet(RestAPIURLs.GET_ALL_EMPLOYEES);
		try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				logger.info("GetAllEmployees API returned 200 OK");
				String responseStr = EntityUtils.toString(response.getEntity());
				EmployeeListDTOAPIResponse employeeApiResponse = objectMapper.readValue(responseStr,
						EmployeeListDTOAPIResponse.class);
				List<EmployeeDTO> employeeList = employeeApiResponse.getData();
				return Optional.ofNullable(employeeList);
				/*
				 * Added explicit handling for Error code 429 because it is observed that this particular 
				 * exception is thrown multiple times 
				 */
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS.value()) {
				logger.error("GetAllEmployees API throttled.");
				String responseStr = EntityUtils.toString(response.getEntity());
				throw new EmployeeAPIThrottledException(HttpStatus.TOO_MANY_REQUESTS.value(), responseStr);
			} else {
				int responseStatus = response.getStatusLine().getStatusCode();
				String responseStr = EntityUtils.toString(response.getEntity());
				logger.error("Response received from getAllEmployees API : " + responseStatus);
				throw new EmployeeAPIException(responseStatus, responseStr);
			}
		}
	}

	/** 
	 * 
	 * Fetches employee by Id
	 * 
	 * @throws EmployeeAPIThrottledException In case too many requests are sent within short span of time
	 * @throws EmployeeAPIException If there is any other exceptional condition
	 * @see com.example.rqchallenge.dao.IEmployeeDao#getEmployeeById(java.lang.String)
	 */
	@Override
	@Retryable(retryFor = ConnectException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
	public Optional<EmployeeDTO> getEmployeeById(String id) throws IOException {

		String url = String.format(RestAPIURLs.GET_EMPLOYEE_BY_ID, id);
		logger.debug("Invoking API: " + url);
		HttpGet httpGet = new HttpGet(url);

		try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				logger.info("Get Employees By Id API returned 200 OK");
				String responseStr = EntityUtils.toString(response.getEntity());

				EmployeeDTOAPIResponse employeeApiResponse = objectMapper.readValue(responseStr,
						EmployeeDTOAPIResponse.class);
				EmployeeDTO employeeDTO = employeeApiResponse.getData();
				return Optional.ofNullable(employeeDTO);

				/*
				 * Added explicit handling for Error code 429 because it is observed that this particular 
				 * exception is thrown multiple times 
				 */
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS.value()) {
				logger.error("Get Employees By Id API throttled.");
				String responseStr = EntityUtils.toString(response.getEntity());
				throw new EmployeeAPIThrottledException(HttpStatus.TOO_MANY_REQUESTS.value(), responseStr);
			} else {
				int responseStatus = response.getStatusLine().getStatusCode();
				String responseStr = EntityUtils.toString(response.getEntity());
				logger.error("Response received from Get Employees By Id  API : " + responseStatus);
				throw new EmployeeAPIException(responseStatus, responseStr);
			}
		}
	}

	/**
	 * 
	 * Creates employee from given input
	 * 
	 * @throws EmployeeAPIThrottledException In case too many requests are sent within short span of time
	 * @throws EmployeeAPIException If there is any other exceptional condition
	 * 
	 * @see com.example.rqchallenge.dao.IEmployeeDao#createEmployee(com.example.rqchallenge.dto.EmployeeDTO)
	 */
	@Override
	@Retryable(retryFor = ConnectException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
	public EmployeeDTO createEmployee(EmployeeDTO inputEmployeeDTO) throws IOException {

		logger.debug("Invoking API: " + RestAPIURLs.CREATE_EMPLOYEE);

		HttpPost httpPost = new HttpPost(RestAPIURLs.CREATE_EMPLOYEE);

		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("employee_name", inputEmployeeDTO.getEmployeeName()));
		params.add(new BasicNameValuePair("employee_salary", String.valueOf(inputEmployeeDTO.getEmployeeSalary())));
		params.add(new BasicNameValuePair("employee_age", String.valueOf(inputEmployeeDTO.getEmployeeAge())));

		httpPost.setEntity(new UrlEncodedFormEntity(params));

		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				logger.info("Create Employee API returned 200 OK");
				String responseStr = EntityUtils.toString(response.getEntity());

				EmployeeDTOAPIResponse employeeApiResponse = objectMapper.readValue(responseStr,
						EmployeeDTOAPIResponse.class);
				EmployeeDTO employeeDTO = employeeApiResponse.getData();
				return employeeDTO;

				/*
				 * Added explicit handling for Error code 429 because it is observed that this particular 
				 * exception is thrown multiple times 
				 */
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS.value()) {
				logger.error("Create Employee  API throttled.");
				String responseStr = EntityUtils.toString(response.getEntity());
				throw new EmployeeAPIThrottledException(HttpStatus.TOO_MANY_REQUESTS.value(), responseStr);
			} else {
				int responseStatus = response.getStatusLine().getStatusCode();
				String responseStr = EntityUtils.toString(response.getEntity());
				logger.error("Response received from Create Employee API : " + responseStatus);
				throw new EmployeeAPIException(responseStatus, responseStr);
			}
		}
	}

	/**
	 * 
	 * Deletes employee for given Id
	 * 
	 * @throws EmployeeAPIThrottledException In case too many requests are sent within short span of time
	 * @throws EmployeeAPIException If there is any other exceptional condition
	 * 
	 * @see com.example.rqchallenge.dao.IEmployeeDao#deleteEmployeeById(java.lang.String)
	 */
	@Override
	@Retryable(retryFor = ConnectException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
	public String deleteEmployeeById(String id) throws IOException {

		String url = String.format(RestAPIURLs.DELETE_EMPLOYEE_BY_ID, id);
		logger.debug("Invoking API: " + url);
		HttpDelete httpDelete = new HttpDelete(url);
		
		/*
		 * Note: Delete employee API always returns Status OK. So alternatively we can first invoke 
		 * getEmployeeById API to check if employee exists or not and then we can invoke delete API.
		 */
		
		try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				logger.info(
						"Delete Employee By Id API returned 200 OK, Successfully deleted employee for input id " + id);
				String responseStr = EntityUtils.toString(response.getEntity());
				TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
				};

				Map<String, String> apiResponse = objectMapper.readValue(responseStr, typeRef);
				return apiResponse.get(Constants.MESSAGE);

				/*
				 * Added explicit handling for Error code 429 because it is observed that this particular 
				 * exception is thrown multiple times 
				 */
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS.value()) {
				logger.error("Delete Employee By Id API throttled.");
				String responseStr = EntityUtils.toString(response.getEntity());
				throw new EmployeeAPIThrottledException(HttpStatus.TOO_MANY_REQUESTS.value(), responseStr);
			} else {
				int responseStatus = response.getStatusLine().getStatusCode();
				String responseStr = EntityUtils.toString(response.getEntity());
				logger.error("Response received from Delete Employee By Id  API : " + responseStatus);
				throw new EmployeeAPIException(responseStatus, responseStr);
			}
		}
	}
}
