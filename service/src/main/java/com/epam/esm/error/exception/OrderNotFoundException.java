package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class OrderNotFoundException extends ApplicationRuntimeException {

    public OrderNotFoundException(long id) {
        super(String.valueOf(id), RestErrorStatusCode.ENTITY_NOT_FOUND);
    }
}
