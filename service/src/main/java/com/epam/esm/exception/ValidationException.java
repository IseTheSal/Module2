package com.epam.esm.exception;

public class ValidationException extends ApplicationRuntimeException {

    public ValidationException(String message, int errorCode) {
        super(message, errorCode);
    }
}
