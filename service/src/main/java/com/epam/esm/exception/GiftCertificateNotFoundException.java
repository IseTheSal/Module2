package com.epam.esm.exception;

public class GiftCertificateNotFoundException extends ApplicationRuntimeException {

    public GiftCertificateNotFoundException(String message, int errorCode) {
        super(message, errorCode);
    }
}
