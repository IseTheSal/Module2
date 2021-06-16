package com.epam.esm.exception;

public class ValidationException extends ApplicationRuntimeException {

    public ValidationException(String message) {
        super(message, RestErrorStatusCode.VALIDATION_ERROR);
    }
}
