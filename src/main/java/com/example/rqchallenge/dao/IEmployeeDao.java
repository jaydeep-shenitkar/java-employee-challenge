package com.example.rqchallenge.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.rqchallenge.model.Employee;

public interface IEmployeeDao {

	Optional<List<Employee>> getAllEmployees();
}
