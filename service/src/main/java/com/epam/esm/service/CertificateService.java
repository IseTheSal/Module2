package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;

public interface CertificateService extends CommonCRDService<GiftCertificate> {

    GiftCertificate update(GiftCertificate giftCertificate);
}
