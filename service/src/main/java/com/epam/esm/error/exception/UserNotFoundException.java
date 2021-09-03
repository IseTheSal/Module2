package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class UserNotFoundException extends ApplicationRuntimeException {

    public UserNotFoundException(String value) {
        super(value, RestErrorStatusCode.ENTITY_NOT_FOUND);
    }

    public UserNotFoundException() {
        super(null, RestErrorStatusCode.ENTITY_NOT_FOUND);
    }
}
