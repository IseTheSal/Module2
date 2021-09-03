package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class UserLoginExistException extends ApplicationRuntimeException {

    public UserLoginExistException(String login) {
        super(login, RestErrorStatusCode.ALREADY_EXIST);
    }
}
