package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class RoleNotFoundException extends ApplicationRuntimeException {

    public RoleNotFoundException(String roleName) {
        super(roleName, RestErrorStatusCode.ENTITY_NOT_FOUND);
    }
}
