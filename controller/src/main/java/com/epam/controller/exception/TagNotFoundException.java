package com.epam.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TagNotFoundException extends CustomRuntimeException {

    public TagNotFoundException(String message, int errorCode) {
        super(message, errorCode);
    }
}
