package com.myservices.loggingservice.exceptions;

import org.springframework.http.HttpStatus;

public class LoggingExceptionResponse {
    private String message;
    private Throwable ex;


    public LoggingExceptionResponse(String message, Throwable ex) {
        this.message = message;
        this.ex = ex;
    }

    public String getMessage() {
        return message;
    }


    public Throwable getEx() {
        return ex;
    }


}
