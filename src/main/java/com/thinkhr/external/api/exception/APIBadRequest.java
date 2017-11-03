package com.thinkhr.external.api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * Custom exception class to handle all the exception those are related with API's bad request
 * @author Surabhi Bhawsar
 * @since 2017-11-03
 *
 */
@Data
public class APIBadRequest extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	private ErrorDetail errorDetail;

	public APIBadRequest(String msg) {
		super(msg);
	}

	/**
	 * @param errorCode
	 * @return
	 */
	public static APIBadRequest createErrorMessage(APIErrorCodes errorCode) {
		APIBadRequest apiBadRequest = new APIBadRequest(errorCode.getMsg());
		apiBadRequest.errorDetail = new ErrorDetail(errorCode.getMsg());
		return apiBadRequest;
	}

	/**
	 * @param errorCode
	 * @param paramList
	 * @return
	 */
	public static APIBadRequest createErrorWithParamsList(APIErrorCodes errorCode, List<String> paramList) {
		return createErrorWithParams(errorCode, paramList.toArray(new String[paramList.size()]));
	}

	/**
	 * @param errorCode
	 * @param paramList
	 * @return
	 */
	public static APIBadRequest createErrorWithParams(APIErrorCodes errorCode, String...paramList) {
	    //TODO: Implement ME
	    // Here dynamic values from error message will be replaced by passed parameters
	    return null;
	}
	
}
