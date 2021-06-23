package com.epam.esm.exception;

public class TagNotFoundException extends ApplicationRuntimeException {

    public TagNotFoundException(long id) {
        super(String.valueOf(id), RestErrorStatusCode.ENTITY_NOT_FOUND);
    }
}
