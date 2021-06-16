package com.epam.esm.exception;

public class GiftCertificateNotFoundException extends ApplicationRuntimeException {

    public GiftCertificateNotFoundException(long id) {
        super(String.valueOf(id), RestErrorStatusCode.ENTITY_NOT_FOUND);
    }
}
