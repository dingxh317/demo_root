package com.yy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yy.model.Employee;

public interface EmployeeDao {
	
	int add(Employee e);
	
	boolean deleteEmployeeById(int id);

	boolean update(Employee e);
	
	Employee getEmployeeById(int id);
	
	/*List<Employee> getEmployees(Map<String,Integer> map);*/
	
	/*当mybatis配置文件中使用MapperScannerConfigurer时，使用@Param注解可实现分页
	List<Employee> getEmployees(@Param("pageno") int pageno,@Param("pagesize") int pagesize);*/
	
	List<Employee> getEmployees(int pageno,int pagesize);

	Integer getLastId();
	
}
