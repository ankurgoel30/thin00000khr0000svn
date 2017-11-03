package com.thinkhr.external.api.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * Wrapper class to wrap all the exception.
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-02
 *
 */
@Data
class APIError {

	private String apiVersion = "v1"; //TODO: Fix me
    private HttpStatus status;
    private String timestamp;
    private ErrorDetail errorDetail;
    DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("YYYY-MM-dd");

    private APIError() {
		timestamp = LocalDateTime.now().format(datePattern);
    }

    APIError(HttpStatus status) {
        this();
        this.status = status;
    }
    
    
    APIError(HttpStatus status, APIErrorCodes errorCodes) {
    	this();
    	this.status = status;
    	this.errorDetail = new ErrorDetail(errorCodes);
    }

    APIError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.errorDetail = new ErrorDetail(ex.getLocalizedMessage());
    }

    APIError(HttpStatus status, ErrorDetail errorDetail) {
        this();
        this.status = status;
        this.errorDetail = errorDetail;
    }

    APIError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.errorDetail = new ErrorDetail(ex.getMessage());
    }

    
}