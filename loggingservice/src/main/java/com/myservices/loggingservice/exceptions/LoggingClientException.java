package com.myservices.loggingservice.exceptions;

public class LoggingClientException extends RuntimeException{
    private String message;
    private Throwable ex;

    public LoggingClientException() {
    }

    public LoggingClientException(String message,  Throwable ex) {
        super(message, ex);
        this.message = message;
        this.ex = ex;
    }

    public LoggingClientException(String message) {
        super(message);
        this.message = message;
    }
}
