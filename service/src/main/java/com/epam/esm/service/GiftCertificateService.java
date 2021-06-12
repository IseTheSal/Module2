package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService extends CommonCRDService<GiftCertificate> {

    GiftCertificate update(GiftCertificate giftCertificate);

    List<GiftCertificate> findByParameters(String tagName, String certificateValue,
                                           String dateSort, String nameSort);
}
