package com.example.rqchallenge.dao;

/**
 * Common class to store all REST end Points
 *
 */
public class RestAPIURLs {
	
	public static final String DUMMY_REST_API_BASE_URL = "https://dummy.restapiexample.com/api/v1/";

	public static final String GET_ALL_EMPLOYEES = DUMMY_REST_API_BASE_URL + "employees";
	public static final String GET_EMPLOYEE_BY_ID = DUMMY_REST_API_BASE_URL + "employee/%s";
	public static final String CREATE_EMPLOYEE = DUMMY_REST_API_BASE_URL + "create";
	public static final String DELETE_EMPLOYEE_BY_ID = DUMMY_REST_API_BASE_URL + "delete/%s";
}
