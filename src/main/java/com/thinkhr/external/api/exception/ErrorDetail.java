package com.thinkhr.external.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * To hold error's detail information like errorCode and message
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-03
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
	private APIErrorCodes errorCode;
    private String message;

    /**
     * @param message
     */
    ErrorDetail(String message) {
        this.message = message;
    }
    
    /**
     * @param message
     * @param errorCode
     */
    public ErrorDetail(APIErrorCodes errorCode) {
    	this.errorCode = errorCode;
    	this.message = errorCode.getMsg();
	}
}