package com.neopane.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class AbstractContainerBaseTest {

	
	static final MySQLContainer MY_SQL;
	
	static {
		
		
		MY_SQL = new MySQLContainer("mysql:latest").withDatabaseName("EMS").withUsername("root").withPassword("password");
		
		MY_SQL.start();
	}
	
 
	
	@DynamicPropertySource
	public static void dynamicProertySource(DynamicPropertyRegistry registry) {
		
		registry.add("spring.datasource.url", MY_SQL::getJdbcUrl);
		registry.add("spring.datasource.username", MY_SQL::getUsername);
		registry.add("spring.datasource.password", MY_SQL::getPassword);
	}
	
}
