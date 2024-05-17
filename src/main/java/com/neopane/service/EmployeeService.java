package com.neopane.service;

import java.util.List;
import java.util.Optional;

import com.neopane.model.Employee;

public interface EmployeeService {
	
	
	Employee  saveEmployee(Employee employee);
	
	List<Employee> getAllEmployees();
	
	Optional<Employee> getEmpByID(long id);
	
	Employee updateEmployee(Employee updatedEmployee);
	
	void deleteEmployeeById(long id);

}
