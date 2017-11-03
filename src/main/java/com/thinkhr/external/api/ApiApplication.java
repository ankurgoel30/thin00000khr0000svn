package com.thinkhr.external.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A starting point for Spring application
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-01
 *
 */

@SpringBootApplication
public class ApiApplication {
	
	/**
	 * main method for spring application
	 * @param args command line arguments passed to app
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
