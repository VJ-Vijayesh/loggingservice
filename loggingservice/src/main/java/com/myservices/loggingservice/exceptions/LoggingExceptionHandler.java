package com.myservices.loggingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LoggingExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({LoggingClientException.class})
    public ResponseEntity<LoggingExceptionResponse> handleClientExceptions(LoggingClientException ex){

        return new ResponseEntity<LoggingExceptionResponse>(new LoggingExceptionResponse(ex.getMessage(),ex),HttpStatus.NOT_FOUND);
    }
}
