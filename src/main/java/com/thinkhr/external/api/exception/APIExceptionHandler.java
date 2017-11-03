package com.thinkhr.external.api.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.thinkhr.external.api.resource.ApplicationMessageHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * This class is exception handler to wrap all the exceptions from API, prepare a Response object 
 * having all the required exception details so user can understand the message and take an action to 
 * fix error with the API.
 * @author Surabhi Bhawsar
 * @since   2017-11-02
 *
 */
@ControllerAdvice
@RestController
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the APIError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new APIError(BAD_REQUEST, error, ex));
    }


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the APIError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        //TODO: Add implementation
    	String errorMessage = APIErrorCodes.MEDIA_NOT_SUPPORTED.getMsg();
        return buildResponseEntity(new APIError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, errorMessage, ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the APIError object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        //TODO: Add implementation
        APIError APIError = new APIError(BAD_REQUEST);
        return buildResponseEntity(APIError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the APIError object
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        //TODO: Add implementation
        APIError APIError = new APIError(BAD_REQUEST);
        return buildResponseEntity(APIError);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the APIError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
        //TODO: Add implementation
        String error = "Malformed JSON request";
        return buildResponseEntity(new APIError(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the APIError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
        //TODO: Add implementation
        String error = "Error writing JSON output";
        return buildResponseEntity(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    /**
     * Handle Exception, handle generic APIBadRequest.class
     *
     * @param ex the Exception
     * @return the APIError object
     */
    @ExceptionHandler(APIBadRequest.class)
    protected ResponseEntity<Object> handleAPIBadRequest(APIBadRequest ex) {
        //TODO: Add Implementation
    	APIError APIError = new APIError(BAD_REQUEST, ex);
        return buildResponseEntity(APIError);
    }


    /**
     * Build Resposne entity for the given error message;
     * @param apiError
     * @return
     */
    private ResponseEntity<Object> buildResponseEntity(APIError apiError) {
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }

    
}
