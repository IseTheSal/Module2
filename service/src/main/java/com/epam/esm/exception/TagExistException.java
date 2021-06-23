package com.epam.esm.exception;

public class TagExistException extends ApplicationRuntimeException {

    public TagExistException(String tagName) {
        super(tagName, RestErrorStatusCode.TAG_EXIST);
    }
}
