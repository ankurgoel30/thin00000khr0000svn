package com.thinkhr.external.api.exception;

import com.thinkhr.external.api.resource.ApplicationMessageHandler;

/**
 * To make an API's error uniquely identifiable by its error code.
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-02
 *
 */
public enum APIErrorCodes {
	
	UNAUTHORIZED_USER(1),
	ARGUMENT_TYPE_MISMATCH(2),
	MEDIA_NOT_SUPPORTED(3);
	
    private final String code;

    APIErrorCodes(int intCode) {
        this.code = Integer.toString(intCode);
    }

    /**
     * @return
     */
    public String getCode() {
        return this.code;
    }
    
    /**
     * @return
     */
    public String getMsg() {
        return ApplicationMessageHandler.getErrorMessage(this.name());
    }

}
