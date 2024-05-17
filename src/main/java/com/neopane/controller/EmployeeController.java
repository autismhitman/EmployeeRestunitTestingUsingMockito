package com.neopane.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neopane.model.Employee;
import com.neopane.service.EmployeeService;

@RequestMapping("/api/employees")
@RestController
public class EmployeeController {
	
	
	
	private EmployeeService empService;
	
	public EmployeeController( EmployeeService empService) {
		
	 this.empService = empService;
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee(@RequestBody Employee employee) {
		
		
		return empService.saveEmployee(employee);
	}
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllEmployees() {
		 return empService.getAllEmployees();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeByID(@PathVariable ("id") long eid) {
		
		return empService.getEmpByID(eid)
				.map(ResponseEntity::ok)
				.orElseGet(()->ResponseEntity.notFound().build());
		  
	}
	
	
	
	@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployees(@RequestBody Employee employee, @PathVariable("id") long eid) {
		
		return empService.getEmpByID(eid)
				.map(savedEmployee-> {
					
					savedEmployee.setFirstName(employee.getFirstName());
					savedEmployee.setLastName(employee.getLastName());
					savedEmployee.setEmail(employee.getEmail());
					
					Employee updatedEmp = empService.updateEmployee(savedEmployee);
					return new ResponseEntity<>(updatedEmp, HttpStatus.OK);
					
				}) 
              
				.orElseGet(()->ResponseEntity.notFound().build());

	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployees(@PathVariable("id") long eid) {
		 
		  empService.deleteEmployeeById(eid);
		  
		  return new ResponseEntity<String>("Employee deleted successfully!", HttpStatus.OK);

	}

}























