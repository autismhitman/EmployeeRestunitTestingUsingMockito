package com.neopane.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.neopane.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTests {
	
	
	@Autowired
	private EmployeeRepository empRepo;
	
	private Employee emp;
	
	
	@BeforeEach
	public void setup() {
		
	        emp = Employee
		         .builder()
		         .firstName("Navin")
		         .lastName("Sharma")
		         .email("neo@gmail.com")
		         .build();
	}
	
	
	//junit test for save employee operation
	@Test
	@DisplayName("Junit Test for save employee operation")
	public void givenEmployeeObject__whenSave_thenReturnSavedEmployee() {
		
		Employee savedEmployee = empRepo.save(emp);
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getFirstName()).isEqualTo("Navin");
		
		
	}
	
	//get all employees
	@Test
	public void givenEmployeesList_whenfindAll_thenEmployeeList() {
		 
		Employee emp2 = Employee
		         .builder()
		         .firstName("Navin1")
		         .lastName("Sharma1")
		         .email("neo1@gmail.com")
		         .build();
		
		empRepo.save(emp);
		empRepo.save(emp2);
		List<Employee>  empList= empRepo.findAll();
		
		assertThat(empList).hasSize(2);
		assertThat(empList.get(0).getFirstName()).isEqualTo("navin");//negative test
		
		
	}
	
	
	@Test//for fetching by id
	public void givenEmployee_whenFindById_thenReturnEployee() {
	 
		 
		empRepo.save(emp);
	    Employee e= empRepo.findById(emp.getId()).get();
		assertThat(e).isNotNull();
		assertThat(e.getEmail()).isEqualTo("neo@gmail.com");
		
	}
	
	@Test //for fetching by email
	public void givenEmployee_whenFindByEmail_thenReturnEmployee() {
		
	    empRepo.save(emp);
		Employee e= empRepo.findByEmail(emp.getEmail()).get();
		System.out.println(e);
		assertThat(e).isNotNull();

	}
	
        @Test //for updating the employee record 
		public void givenEmployeeObject_whenUpdating_thenEmployeeRecordUpdated() {

         empRepo.save(emp);
         Employee e = empRepo.findById(emp.getId()).get();
         e.setFirstName("Virat");
         Employee updatedEmployee = empRepo.save(e);
         assertThat(updatedEmployee.getFirstName()).isEqualTo("Virat");
        	
		}
        
        
        
        @Test//delete employee operation
        public void givenEmployee_whenRecordDeleted_thenEmployeeRecordDeleted() {
          
            empRepo.save(emp);
       		empRepo.delete(emp);
        	Optional<Employee> eo = empRepo.findById(emp.getId());
       		assertThat(eo).isEmpty();
        	
		}
        
        
        //jpql with index parameters
        @DisplayName(" using JPQL with Index Params")
        @Test
		public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        	empRepo.save(emp);
        	Employee savedEmployee = empRepo.findByJPQL(emp.getFirstName(), emp.getLastName());
        	assertThat(savedEmployee).isNotNull();
        	
		}
        
         
        @DisplayName(" using JPQL with NamedParams")
        @Test
        public void givenFirstNameAndLastNameParams_whenFindByJPQL_thenReturnEmployeeObject() {
 
      		empRepo.save(emp);
      		Employee savedEmployee = empRepo.findByJPQL(emp.getFirstName(), emp.getLastName());
      		assertThat(savedEmployee).isNotNull();
           	
   		}
        
         
        
        //Native Query with index parameters
        @DisplayName("Native Query with index parameters")
        @Test
		public void givenFirstNameAndLastName_whenfindByNativeIndexParams_thenReturnEmployeeObject() {

         empRepo.save(emp);
   		 Employee savedEmployee = empRepo.findByNativeIndexParams(emp.getFirstName(), emp.getLastName());
   		 assertThat(savedEmployee).isNotNull();
        	
		}
        
        
        //Native Query with Named parameters
        @DisplayName("Native Query with named parameters")
        @Test
		public void givenFirstNameAndLastName_whenNativeNamedParams_thenReturnEmployeeObject() {

         empRepo.save(emp);
   		 Employee savedEmployee = empRepo.findByNativeNamedParams(emp.getFirstName(), emp.getLastName());
   		 assertThat(savedEmployee).isNotNull();
        	
		}
        
        
        
        
        
        
        
        
        
}
