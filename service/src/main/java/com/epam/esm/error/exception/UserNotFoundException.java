package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class UserNotFoundException extends ApplicationRuntimeException {

    public UserNotFoundException(long id) {
        super(String.valueOf(id), RestErrorStatusCode.ENTITY_NOT_FOUND);
    }
}
