package com.epam.esm.error.exception;

public abstract class ApplicationRuntimeException extends RuntimeException {

    protected int errorCode;

    public ApplicationRuntimeException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
