package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class TagExistException extends ApplicationRuntimeException {

    public TagExistException(String tagName) {
        super(tagName, RestErrorStatusCode.TAG_EXIST);
    }
}
