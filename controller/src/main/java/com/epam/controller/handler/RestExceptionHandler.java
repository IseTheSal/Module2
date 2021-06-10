package com.epam.controller.handler;

import com.epam.exception.ApplicationRuntimeException;
import com.epam.exception.RestApplicationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class RestExceptionHandler {
    //fixme localize exception
    @ExceptionHandler(ApplicationRuntimeException.class)
    public ResponseEntity<RestApplicationError> applicationErrorHandler(ApplicationRuntimeException exception) {
        RestApplicationError error = new RestApplicationError(exception.getMessage(), exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApplicationError> defaultErrorHandler() {
        RestApplicationError error = new RestApplicationError("Bad request", 40403);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestApplicationError> unknownErrorHandler() {
        RestApplicationError error = new RestApplicationError("Incorrect request", 40404);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.NOT_FOUND);
    }
}
