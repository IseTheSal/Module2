//package com.epam.esm.service.impl;
//
//import com.epam.esm.model.dto.GiftCertificateDTO;
//import com.epam.esm.model.entity.GiftCertificate;
//import com.epam.esm.model.entity.Tag;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//public class MockData {
//
//    static final GiftCertificateDTO GIFT_CERTIFICATE;
//    static final GiftCertificateDTO UPDATED_GIFT_CERTIFICATE;
//    static final Tag TAG_ONE;
//    static final Tag TAG_TWO;
//
//    static {
//        GiftCertificate certificate = new GiftCertificate();
//        certificate.setId(1);
//        certificate.setName("Квадроцикл");
//        certificate.setDescription("Увлекательное катание на квадроцикле");
//        certificate.setPrice(new BigDecimal("20.20"));
//        certificate.setDuration(3);
//        LocalDateTime dateTime = LocalDateTime.now();
//        certificate.setCreateDate(dateTime);
//        certificate.setLastUpdateDate(dateTime);
//        TAG_ONE = new Tag("машина");
//        TAG_ONE.setId(1);
//        TAG_TWO = new Tag("экстрим");
//        TAG_TWO.setId(2);
//        Set<Tag> tagSet = new HashSet<>();
//        tagSet.add(TAG_ONE);
//        tagSet.add(TAG_TWO);
//        tagSet.forEach(certificate::addTag);
//        GIFT_CERTIFICATE = GiftCertificateDTO.toDTO(certificate);
//        UPDATED_GIFT_CERTIFICATE = new GiftCertificate();
//        UPDATED_GIFT_CERTIFICATE.setId(certificate.getId());
//        UPDATED_GIFT_CERTIFICATE.setName("Обновленное");
//        UPDATED_GIFT_CERTIFICATE.setDescription(certificate.getDescription());
//        UPDATED_GIFT_CERTIFICATE.setPrice(certificate.getPrice());
//        UPDATED_GIFT_CERTIFICATE.setDuration(certificate.getDuration());
//        UPDATED_GIFT_CERTIFICATE.setCreateDate(certificate.getCreateDate());
//        UPDATED_GIFT_CERTIFICATE.setLastUpdateDate(LocalDateTime.now());
//
//
//    }
//}
