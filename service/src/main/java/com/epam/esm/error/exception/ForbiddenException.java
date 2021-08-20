package com.epam.esm.error.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationRuntimeException{

    public ForbiddenException() {
        super("FORBIDDEN", HttpStatus.FORBIDDEN.value());
    }
}
