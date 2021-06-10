package com.epam.exception;

public class ValidationException extends ApplicationRuntimeException {

    public ValidationException(String message, int errorCode) {
        super(message, errorCode);
    }
}
