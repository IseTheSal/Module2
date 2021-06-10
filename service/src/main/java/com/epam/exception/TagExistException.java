package com.epam.exception;

public class TagExistException extends ApplicationRuntimeException {

    public TagExistException(String message, int errorCode) {
        super(message, errorCode);
    }
}
