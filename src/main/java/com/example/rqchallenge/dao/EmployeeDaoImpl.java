package com.example.rqchallenge.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.rqchallenge.constatnts.RestAPIURLs;
import com.example.rqchallenge.exception.EmployeeAPIException;
import com.example.rqchallenge.exception.EmployeeAPIThrottledException;
import com.example.rqchallenge.exception.ErrorCode;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeAPIResponse;
import com.example.rqchallenge.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmployeeDaoImpl implements IEmployeeDao {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public Optional<List<Employee>> getAllEmployees() {

		logger.info("Making API CALL ");

		HttpGet httpGet = new HttpGet(RestAPIURLs.GET_ALL_EMPLOYEES);

		try (CloseableHttpResponse response = httpClient.execute(httpGet);) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

				String responseStr = EntityUtils.toString(response.getEntity());
				EntityUtils.consumeQuietly(response.getEntity());
				EmployeeAPIResponse employeeApiResponse = objectMapper.readValue(responseStr,
						EmployeeAPIResponse.class);
				List<Employee> employeeList = employeeApiResponse.getData();
				
				return Optional.ofNullable(employeeList);

			} else if (response.getStatusLine().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS.value()) {
				String responseStr = EntityUtils.toString(response.getEntity());
				EntityUtils.consumeQuietly(response.getEntity());
				throw new EmployeeAPIThrottledException(HttpStatus.TOO_MANY_REQUESTS.value(), responseStr);
			} else {
				int responseStatus = response.getStatusLine().getStatusCode();
				String responseStr = EntityUtils.toString(response.getEntity());
				EntityUtils.consumeQuietly(response.getEntity());
				throw new EmployeeAPIException(responseStatus, responseStr);
			}

		} catch (IOException e) {
			logger.error("Exception while making an API call", e);
			throw new EmployeeAPIException(ErrorCode.FAILED_TO_PREPARE_ES_QUERY.getCode(), e.getMessage());
		}

	}

}
