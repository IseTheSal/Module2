package com.epam.esm.service.impl;

import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.impl.JpaGiftCertificateImpl;
import com.epam.esm.model.dao.impl.JpaTagImpl;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;

import java.util.*;

class CertificateServiceImplTest {

    GiftCertificateService service;
    GiftCertificateDao dao;
    MessageSource messageSource;
    TagDao tagDao;

    @BeforeEach
    public void setUp() {
        dao = Mockito.mock(JpaGiftCertificateImpl.class);
        messageSource = Mockito.mock(MessageSource.class);
        tagDao = Mockito.mock(JpaTagImpl.class);
        service = new GiftCertificateServiceImpl(dao, tagDao, messageSource);
    }

    @Test
    void create() {
        Mockito.when(dao.create(MockData.GIFT_CERTIFICATE)).thenReturn(MockData.GIFT_CERTIFICATE);
        GiftCertificate actual = service.create(MockData.GIFT_CERTIFICATE);
        GiftCertificate expected = MockData.GIFT_CERTIFICATE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(MockData.GIFT_CERTIFICATE));
        Mockito.when(dao.update(ArgumentMatchers.any())).thenReturn(MockData.UPDATED_GIFT_CERTIFICATE);
        GiftCertificate actual = service.update(MockData.UPDATED_GIFT_CERTIFICATE);
        GiftCertificate expected = MockData.UPDATED_GIFT_CERTIFICATE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findById() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.ofNullable(MockData.GIFT_CERTIFICATE));
        GiftCertificate actual = service.findById(1);
        GiftCertificate expected = MockData.GIFT_CERTIFICATE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        Mockito.when(dao.delete(1)).thenReturn(true);
        long actual = service.delete(1);
        long expected = 1;
        Assertions.assertSame(expected, actual);
    }

    @Test
    void deleteThrowException() {
        Mockito.when(dao.delete(8)).thenReturn(false);
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> service.delete(8));
    }

    @Test
    void findAll() {
        Mockito.when(dao.findAll(100, 0)).thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        List<GiftCertificate> actual = service.findAll(100, 1);
        List<GiftCertificate> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByParameters() {
        Mockito.when(dao.findByAttributes(MockData.TAG_TWO.getName(), "катание", "DESC",
                "ASC", 100, 0))
                .thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        List<GiftCertificate> actual = service.findByParameters(MockData.TAG_TWO.getName(), "катание",
                "DESC", "ASC", 100, 1);
        List<GiftCertificate> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void removeFromSales() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(MockData.GIFT_CERTIFICATE));
        Mockito.when(dao.update(MockData.GIFT_CERTIFICATE)).thenReturn(MockData.GIFT_CERTIFICATE);
        long actual = service.removeFromSale(MockData.GIFT_CERTIFICATE.getId());
        long expected = MockData.GIFT_CERTIFICATE.getId();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findBySeveralTags() {
        Mockito.when(tagDao.findByName(MockData.TAG_ONE.getName())).thenReturn(Optional.of(MockData.TAG_ONE));
        Mockito.when(tagDao.findByName(MockData.TAG_TWO.getName())).thenReturn(Optional.of(MockData.TAG_TWO));
        String[] tagNames = new String[]{MockData.TAG_ONE.getName(), MockData.TAG_TWO.getName()};
        Mockito.when(dao.findBySeveralTags(tagNames, 100, 0))
                .thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(MockData.TAG_TWO);
        tagSet.add(MockData.TAG_ONE);
        List<GiftCertificate> actual = service.findBySeveralTags(tagSet, 100, 1);
        List<GiftCertificate> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE);
        Assertions.assertEquals(expected, actual);
    }
}