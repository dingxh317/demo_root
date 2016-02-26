package com.yy.service;

import java.util.List;
import java.util.Map;

import com.yy.model.Employee;

public interface EmployeeService {
	
	int add(Employee e);
	
	boolean deleteEmployeeById(int id);

	boolean update(Employee e);
	
	Employee getEmployeeById(Employee employee);
	
	List<Employee> getEmployees(Map<String,Integer> map);
	
	List<Employee> getEmployees();
	
	Integer getLastId();
}
