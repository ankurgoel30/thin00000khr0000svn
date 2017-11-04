package com.thinkhr.external.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

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
	
	/**
	 * @return
	 */
	@Bean
	public MessageSource messageSource () {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("errors");
		return messageSource;
	}
	
	/**
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
