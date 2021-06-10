package com.epam.exception;

public class TagNotFoundException extends ApplicationRuntimeException {

    public TagNotFoundException(String message, int errorCode) {
        super(message, errorCode);
    }
}
