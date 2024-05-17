package com.neopane.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neopane.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	
	Optional<Employee> findByEmail(String email);
	
	//jpql with index parameters
	@Query("select e from Employee e where e.firstName=?1 and e.lastName=?2")
	Employee findByJPQL (String firstName, String lastName);
 
	
	//jpql with named parameters
	 @Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
     Employee findByJPQLNamedParams (@Param("firstName") String firstName, @Param("lastName") String lastName);
		
	 //Native Query with index  parameters
	 @Query(value="select * from Employees e where e.first_name=?1 and e.last_Name=?2 ", nativeQuery= true)
	 Employee findByNativeIndexParams ( String firstName,   String lastName);	
		
	//Native Query with named parameters
     @Query(value="select * from Employees e where e.first_name=:firstName and e.last_Name=:lastName ", nativeQuery= true)
    Employee findByNativeNamedParams (@Param("firstName") String firstName, @Param("lastName") String lastName);		
		
}
