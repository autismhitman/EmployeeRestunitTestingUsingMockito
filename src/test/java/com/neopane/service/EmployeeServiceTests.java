package com.neopane.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neopane.exception.ResourceNotFoundException;
import com.neopane.model.Employee;
import com.neopane.repository.EmployeeRepository;
import com.neopane.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
	
	private Employee emp;
	
	
	@Mock
	private EmployeeRepository empRepo;
	
	
	@InjectMocks
	private EmployeeServiceImpl empService;
	
	@BeforeEach
	public void setup() {
		
		  emp = Employee
			         .builder()
			         .id(1L)
			         .firstName("Navin")
			         .lastName("Sharma")
			         .email("neo@gmail.com")
			         .build();
	}
	
	@DisplayName("Junit test for save employee when email doesn't exist")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
		
		BDDMockito.given(empRepo.findByEmail(emp.getEmail())).willReturn(Optional.empty());
		BDDMockito.given(empRepo.save(emp)).willReturn(emp);
		
		System.out.println(empRepo);
		Employee savedEmployee = empService.saveEmployee(emp);
		System.out.println(savedEmployee);
		//BDDMockito.then(empRepo).should().findByEmail(emp.getEmail());
		//BDDMockito.then(empRepo).should().save(emp);
		
		assertThat(savedEmployee).isNotNull();
		
	}
	
	@DisplayName("Junit test for save employee when email exist it throws exception")
	@Test
	public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
		
		BDDMockito.given(empRepo.findByEmail(emp.getEmail())).willReturn(Optional.of(emp));
	 
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()-> empService.saveEmployee(emp));
        
        Mockito.verify(empRepo, never()).save(any(Employee.class));
           
	 
		
	}
	
	@DisplayName("Junit test for getAll employee")
	@Test
	public void givenempList_whenGetAllEmp_thenReturnEmpList() {
		
	Employee emp1=	Employee
			        .builder()
			        .id(1L)
			        .firstName("Navin1")
			        .lastName("Sharma1")
			        .email("neo1@gmail.com")
			        .build();
        
		List<Employee> empList= new ArrayList<>();
		empList.add(emp);		
		empList.add(emp1);
		
		BDDMockito.given(empRepo.findAll()).willReturn(empList);
		
		List<Employee> empList2 = empService.getAllEmployees();
		
		assertThat(empList2).hasSize(2);
		assertThat(empList2).isNotNull();
		
	}
	

	@DisplayName("Junit test for getAll employee when list is empty")
	@Test
	public void givenemptyempList_whenGetAllEmp_thenReturnEmpList() {
		
		BDDMockito.given(empRepo.findAll()).willReturn(Collections.emptyList());
		List<Employee> empList2 = empService.getAllEmployees();
		assertThat(empList2).isEmpty();
		assertThat(empList2.size()).isEqualTo(0);
		
	}
	
	
	@DisplayName("Junit test for getEmployee by id ")
	@Test
	public void givenEmpId_whenGetEmpById_thenReturnEmp() {
		
		 BDDMockito.given(empRepo.findById(1L)).willReturn(Optional.of(emp));
		 
	     Employee emp1= empService.getEmpByID(emp.getId()).get();
		 
		 assertThat(emp1).isNotNull();
		 
		
	}
	
	
	@DisplayName("Junit test for updating the employee")
	@Test
	public void givenEmpId_whenGetEmpById_thenupdateEmp() {
		
	  	BDDMockito.given(empRepo.save(emp)).willReturn(emp);
	  	
		 emp.setEmail("changed@gmail.com");
		 emp.setFirstName("Niki");
		 emp.setLastName("Lauda");
		 
		 
		 
		 Employee emp1= empService.updateEmployee(emp);
		 
		// assertThat(emp1).isNotNull();
		 assertThat(emp1.getEmail()).isEqualTo(emp.getEmail());
		 System.out.println( "EMP1 -> "+ emp1);
		 System.out.println( "EMP -> "+ emp);
	}
	
	
	
	@DisplayName("Junit test for deleting an employee")
	@Test
	public void givenEmpId_whenGetEmpById_thenNothing() {
		
	  	BDDMockito.doNothing().when(empRepo).deleteById(emp.getId());
	  	
	  	empService.deleteEmployeeById(1L);
	  	
	  	BDDMockito.verify(empRepo, times(1)).deleteById(1L);

	 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
