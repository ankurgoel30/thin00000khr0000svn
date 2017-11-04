package com.thinkhr.external.api.exception;

import lombok.Getter;

/**
 * To make an API's error uniquely identifiable by its error code.
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-02
 *
 */
public enum APIErrorCodes {
	
	VALIDATION_FAILED(1000),
	REQUIRED_PARAMETER(1000),
	UNAUTHORIZED_USER(1001),
	ARGUMENT_TYPE_MISMATCH(1002),
	MEDIA_NOT_SUPPORTED(1003),
	ENTITY_NOT_FOUND(1004),
	MALFORMED_JSON_REQUEST(1000),
	ERROR_WRITING_JSON_OUTPUT(1005),
	DATABASE_ERROR(1006);
	
	@Getter
    private final Integer code;

    APIErrorCodes(Integer intCode) {
        this.code = intCode;
    }

}
