package com.neopane.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neopane.exception.ResourceNotFoundException;
import com.neopane.model.Employee;
import com.neopane.repository.EmployeeRepository;
import com.neopane.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
 
	// @Autowired --this is not reqd as constructor is being used/
	private EmployeeRepository empRepo;
	
	
	
	public EmployeeServiceImpl(EmployeeRepository empRepo) {
		super();
		this.empRepo = empRepo;
	}



	@Override
	public Employee saveEmployee(Employee employee) {
		 
		Optional<Employee> emp = empRepo.findByEmail(employee.getEmail());
		if(emp.isPresent()) {
			
			throw new ResourceNotFoundException("Resource already exists : " + employee.getEmail());
		}
		return empRepo.save(employee);
	}



	@Override
	public List<Employee> getAllEmployees() {
		 
          
		return empRepo.findAll();
 
	}



	@Override
	public Optional<Employee> getEmpByID(long id) {
		 return empRepo.findById(id) ;
  
	}



	@Override
	public Employee updateEmployee(Employee updatedEmployee) {
		
		return empRepo.save(updatedEmployee);
	  
	}



	@Override
	public void deleteEmployeeById(long id) {
		 
		 empRepo.deleteById(id);
	}

}
