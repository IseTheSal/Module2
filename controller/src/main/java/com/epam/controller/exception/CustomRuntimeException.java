package com.epam.controller.exception;

public abstract class CustomRuntimeException extends RuntimeException {

    protected int errorCode;

    public CustomRuntimeException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
