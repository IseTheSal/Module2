package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class MockData {

    static final GiftCertificate GIFT_CERTIFICATE;
    static final GiftCertificate UPDATED_GIFT_CERTIFICATE;
    static final Tag TAG_ONE;
    static final Tag TAG_TWO;

    static {
        GIFT_CERTIFICATE = new GiftCertificate();
        GIFT_CERTIFICATE.setId(1);
        GIFT_CERTIFICATE.setName("Квадроцикл");
        GIFT_CERTIFICATE.setDescription("Увлекательное катание на квадроцикле");
        GIFT_CERTIFICATE.setPrice(new BigDecimal("20.20"));
        GIFT_CERTIFICATE.setDuration(3);
        LocalDateTime dateTime = LocalDateTime.now();
        GIFT_CERTIFICATE.setCreateDate(dateTime);
        GIFT_CERTIFICATE.setLastUpdateDate(dateTime);
        TAG_ONE = new Tag("машина");
        TAG_ONE.setId(1);
        TAG_TWO = new Tag("экстрим");
        TAG_TWO.setId(2);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(TAG_ONE);
        tagSet.add(TAG_TWO);
        tagSet.forEach(GIFT_CERTIFICATE::addTag);
        UPDATED_GIFT_CERTIFICATE = new GiftCertificate();
        UPDATED_GIFT_CERTIFICATE.setId(GIFT_CERTIFICATE.getId());
        UPDATED_GIFT_CERTIFICATE.setName("Обновленное");
        UPDATED_GIFT_CERTIFICATE.setDescription(GIFT_CERTIFICATE.getDescription());
        UPDATED_GIFT_CERTIFICATE.setPrice(GIFT_CERTIFICATE.getPrice());
        UPDATED_GIFT_CERTIFICATE.setDuration(GIFT_CERTIFICATE.getDuration());
        UPDATED_GIFT_CERTIFICATE.setCreateDate(GIFT_CERTIFICATE.getCreateDate());
        UPDATED_GIFT_CERTIFICATE.setLastUpdateDate(LocalDateTime.now());


    }
}
