package com.bhreneer.springdatacrudexample.exception;

import org.springframework.http.HttpStatus;

public class ValidateException extends RuntimeException{

    private String message;
    private HttpStatus httpStatus;

    public ValidateException(String message, HttpStatus httpStatus){
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
