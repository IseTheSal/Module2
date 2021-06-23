package com.epam.esm.controller.handler;

import com.epam.esm.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ApplicationRuntimeException.class)
    public ResponseEntity<RestApplicationError> applicationErrorHandler(ApplicationRuntimeException exception) {
        RestApplicationError error = new RestApplicationError(exception.getMessage(), exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RestApplicationError> validationErrorHandler(ValidationException exception) {
        RestApplicationError error = new RestApplicationError(exception.getMessage(), exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TagExistException.class)
    public ResponseEntity<RestApplicationError> tagExistException(TagExistException exception) {
        String name = exception.getMessage();
        String exceptionMessage = messageSource.getMessage("error.tag.exist.name", new Object[]{name},
                LocaleContextHolder.getLocale());
        RestApplicationError error = new RestApplicationError(exceptionMessage, exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<RestApplicationError> tagNotFoundHandler(TagNotFoundException exception) {
        String id = exception.getMessage();
        String exceptionMessage = messageSource.getMessage("error.tag.not.found", new Object[]{id},
                LocaleContextHolder.getLocale());
        RestApplicationError error = new RestApplicationError(exceptionMessage, exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    public ResponseEntity<RestApplicationError> giftNotFoundHandler(GiftCertificateNotFoundException exception) {
        String id = exception.getMessage();
        String exceptionMessage = messageSource.getMessage("error.gift.not.found", new Object[]{id},
                LocaleContextHolder.getLocale());
        RestApplicationError error = new RestApplicationError(exceptionMessage, exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<RestApplicationError> invalidRequestBody() {
        RestApplicationError error = new RestApplicationError(messageSource.getMessage(
                "error.handler.incorrect.body", null, LocaleContextHolder.getLocale()),
                RestErrorStatusCode.VALIDATION_ERROR);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestApplicationError> invalidRequestParam() {
        RestApplicationError error = new RestApplicationError(messageSource.getMessage(
                "error.handler.incorrect.parameters", null, LocaleContextHolder.getLocale()),
                RestErrorStatusCode.VALIDATION_ERROR);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApplicationError> defaultErrorHandler() {
        RestApplicationError error = new RestApplicationError(messageSource.getMessage(
                "error.handler.incorrect.request", null, LocaleContextHolder.getLocale()), 40403);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestApplicationError> unknownErrorHandler() {
        RestApplicationError error = new RestApplicationError(messageSource.getMessage(
                "error.handler.incorrect.request", null, LocaleContextHolder.getLocale()), 40404);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.NOT_FOUND);
    }
}
