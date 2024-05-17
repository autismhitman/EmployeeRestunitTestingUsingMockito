package com.neopane.controller;

import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neopane.model.Employee;
import com.neopane.service.EmployeeService;

@WebMvcTest
public class EmployeeControllerTests {
	
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean//tells the spring to mock object and  register in application context
	private EmployeeService empService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	private Employee emp;
	
	@BeforeEach
	public void setUP() {
		 emp = Employee
	               .builder()
	               .id(1L)
	               .firstName("Navin")
	               .lastName("Sharma")
	               .email("neo@gmail.com")
	               .build();
	
		
	}
	
	@DisplayName("Unit test createEmployee REST API")
	@Test
	public void givenEmp_whenPost_thenEmployeeeCreated() throws Exception {
		
	   
		BDDMockito.given(empService.saveEmployee(ArgumentMatchers.any(Employee.class)))
		.willAnswer((invocation)->invocation.getArgument(0));
		
		ResultActions response= mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(emp))
								);
		
		 response.andDo(MockMvcResultHandlers.print())
		         .andExpect( MockMvcResultMatchers.status().isCreated())
		         .andExpect( MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(emp.getFirstName())));
		       
	}
	
	
	@DisplayName("Unit test GetAllEmployees REST API")
	@Test
	public void givenEmp_whenGet_thenGetAllEmployees() throws Exception {
		
		Employee emp1 = Employee
		               .builder()
		               .id(2L)
		               .firstName("Sam")
		               .lastName("Sharma")
		               .email("sam@gmail.com")
		               .build();
		
		 List<Employee> empList = List.of(emp,emp1);
		 
		 BDDMockito.given(empService.getAllEmployees()).willReturn(empList);
		 
		 ResultActions response   = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees")
		            		 .contentType(MediaType.APPLICATION_JSON) );
		            		
		  response.andDo(MockMvcResultHandlers.print()) 
		          .andExpectAll(MockMvcResultMatchers.status().isOk())
		          .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(empList.size())))
		          .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is(empList.get(1).getFirstName())));
	 	       
	}
	
	@DisplayName("Unit test getEmployeeById REST API - Positive Scenario")
	@Test
	public void givenEmp_whenGetByID_thenGetEmployee() throws Exception {
		
		 long eid= 1L;
		 BDDMockito.given(empService.getEmpByID(1L)).willReturn(Optional.of(emp));
		 
		 ResultActions response   = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", eid)
        		                    .contentType(MediaType.APPLICATION_JSON) );
		 
		 response.andDo(MockMvcResultHandlers.print()) 
         .andExpectAll(MockMvcResultMatchers.status().isOk());

	}
	
	
	@DisplayName("Unit test getEmployeeById REST API - Negative Scenario")
	@Test
	public void givenNotExistingEmpID_whenGetByID_thenException() throws Exception {
		
		 long eid= 2L;
		 BDDMockito.given(empService.getEmpByID(eid)).willReturn(Optional.empty());
		 
		 ResultActions response   = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", eid)
        		                    .contentType(MediaType.APPLICATION_JSON) );
		 
		 response.andDo(MockMvcResultHandlers.print()) 
         .andExpectAll(MockMvcResultMatchers.status().isNotFound());

	}
	
	@DisplayName("Unit test updateEmployee REST API - Positive Scenario")
	@Test
	public void givenEmp_whenUpdate_thenReturnupdatedEmployeeObject() throws Exception {
		
		 long eid= 1L;
		 
		 
			Employee updatedEmp = Employee
		               .builder()
		               .firstName("Sam")
		               .lastName("Sharma")
		               .email("sam@gmail.com")
		               .build();
			
		 BDDMockito.given(empService.getEmpByID(eid)).willReturn(Optional.of(emp));
		 
		 BDDMockito.given(empService.updateEmployee(ArgumentMatchers.any(Employee.class)))
			.willAnswer((invocation)->invocation.getArgument(0));
		 
		 
		 ResultActions response= mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", eid)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedEmp))
					);
		 
		 response.andDo(MockMvcResultHandlers.print()) 
         		  .andExpect( MockMvcResultMatchers.status().isOk())
         		  .andExpect( MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmp.getFirstName())));
 
	}
	
	
	@DisplayName("Unit test updateEmployee REST API - Negative Scenario")
	@Test
	public void givenExistingEmpID_whenUpdate_thenReturnException() throws Exception {
		
		     long eid= 1L;
		 
		 
			Employee updatedEmp = Employee
		               .builder()
		               .firstName("Sam")
		               .lastName("Sharma")
		               .email("sam@gmail.com")
		               .build();
			
		 BDDMockito.given(empService.getEmpByID(eid)).willReturn(Optional.empty());
		 
		 BDDMockito.given(empService.updateEmployee(ArgumentMatchers.any(Employee.class)))
			.willAnswer((invocation)->invocation.getArgument(0));
		 
		 
		 ResultActions response= mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", eid)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedEmp))
					);
		 
		 response.andDo(MockMvcResultHandlers.print()) 
         		  .andExpect( MockMvcResultMatchers.status().isNotFound());
         		   
 
	}
	
	

	@DisplayName("Unit test deleteEmployee REST API")
	@Test
	public void givenExistingEmpID_whenDelete_thenNoReturn() throws Exception {
		
		long eid = 1L;
		
		BDDMockito.doNothing().when(empService).deleteEmployeeById(eid);
		
		ResultActions response= mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", eid)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andDo(MockMvcResultHandlers.print()) 
		  .andExpect( MockMvcResultMatchers.status().isOk());
		
	}
	
	
	
	
	
	
	
}
