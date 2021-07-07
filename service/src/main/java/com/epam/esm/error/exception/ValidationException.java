package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class ValidationException extends ApplicationRuntimeException {

    public ValidationException(String message) {
        super(message, RestErrorStatusCode.VALIDATION_ERROR);
    }
}
