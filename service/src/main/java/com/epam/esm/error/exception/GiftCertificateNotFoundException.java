package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class GiftCertificateNotFoundException extends ApplicationRuntimeException {

    public GiftCertificateNotFoundException(long id) {
        super(String.valueOf(id), RestErrorStatusCode.ENTITY_NOT_FOUND);
    }
}
