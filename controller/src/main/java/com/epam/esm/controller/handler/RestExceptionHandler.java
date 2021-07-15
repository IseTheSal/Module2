package com.epam.esm.controller.handler;

import com.epam.esm.error.RestApplicationError;
import com.epam.esm.error.RestErrorStatusCode;
import com.epam.esm.error.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j2;
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

import java.util.Locale;

@RestControllerAdvice
@Log4j2
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
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.CONFLICT);
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
        Locale locale = LocaleContextHolder.getLocale();
        String exceptionMessage;
        if (id != null) {
            exceptionMessage = messageSource.getMessage("error.tag.not.found", new Object[]{id}, locale);
        } else {
            exceptionMessage = messageSource.getMessage("error.tag.not.found.message", null, locale);
        }
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestApplicationError> userNotFoundHandler(UserNotFoundException exception) {
        String id = exception.getMessage();
        String exceptionMessage = messageSource.getMessage("error.user.not.found", new Object[]{id},
                LocaleContextHolder.getLocale());
        RestApplicationError error = new RestApplicationError(exceptionMessage, exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<RestApplicationError> userNotFoundHandler(OrderNotFoundException exception) {
        String id = exception.getMessage();
        String exceptionMessage = messageSource.getMessage("error.order.not.found", new Object[]{id},
                LocaleContextHolder.getLocale());
        RestApplicationError error = new RestApplicationError(exceptionMessage, exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<RestApplicationError> invalidRequestBody(InvalidFormatException ex) {
        log.error(ex.getMessage());
        RestApplicationError error = new RestApplicationError(messageSource.getMessage(
                "error.handler.incorrect.body", null, LocaleContextHolder.getLocale()),
                RestErrorStatusCode.VALIDATION_ERROR);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestApplicationError> invalidRequestParam(MethodArgumentTypeMismatchException ex) {
        log.error(ex.getMessage());
        RestApplicationError error = new RestApplicationError(messageSource.getMessage(
                "error.handler.incorrect.parameters", null, LocaleContextHolder.getLocale()),
                RestErrorStatusCode.VALIDATION_ERROR);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, NoHandlerFoundException.class})
    public ResponseEntity<RestApplicationError> defaultErrorHandler(Exception ex) {
        log.error(ex.getMessage());
        RestApplicationError error = new RestApplicationError(messageSource.getMessage(
                "error.handler.incorrect.request", null, LocaleContextHolder.getLocale()), 40400);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
    }
}
