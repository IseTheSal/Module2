package com.epam.service;

import com.epam.model.entity.GiftCertificate;

public interface CertificateService extends CommonCRDService<GiftCertificate> {

    GiftCertificate update(GiftCertificate giftCertificate);
}
