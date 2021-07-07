package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class IncorrectPageException extends ApplicationRuntimeException {

    public IncorrectPageException(int amount, int page) {
        super(amount + "&" + page, RestErrorStatusCode.VALIDATION_ERROR);
    }
}