package com.aaroncarlson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * NOTE: Both Transactional and Exception Handling is NOT enabled (the based components are exposed but no implemented)
 */
@SpringBootApplication
public class RideTrackerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RideTrackerApplication.class, args);
	}

}
