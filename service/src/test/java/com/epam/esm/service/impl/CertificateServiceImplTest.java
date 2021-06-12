package com.epam.esm.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class CertificateServiceImplTest {

    GiftCertificateService service;
    GiftCertificateDao dao;

    @BeforeEach
    private void setUp() {
        dao = Mockito.mock(GiftCertificateDao.class);
        service = new GiftCertificateServiceImpl(dao);
    }

    @Test
    public void create() {
        Mockito.when(dao.create(ArgumentMatchers.any())).thenReturn(MockData.GIFT_CERTIFICATE);
        GiftCertificate actual = service.create(MockData.GIFT_CERTIFICATE);
        GiftCertificate expected = MockData.GIFT_CERTIFICATE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Mockito.when(dao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(MockData.GIFT_CERTIFICATE));
        Mockito.when(dao.update(ArgumentMatchers.any())).thenReturn(MockData.UPDATED_GIFT_CERTIFICATE);
        GiftCertificate actual = service.update(MockData.UPDATED_GIFT_CERTIFICATE);
        GiftCertificate expected = MockData.UPDATED_GIFT_CERTIFICATE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findById() {
        Mockito.when(dao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(MockData.GIFT_CERTIFICATE));
        GiftCertificate actual = service.findById(String.valueOf(1));
        GiftCertificate expected = MockData.GIFT_CERTIFICATE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Mockito.when(dao.delete(1)).thenReturn(true);
        long actual = service.delete(String.valueOf(1));
        long expected = 1;
        Assertions.assertSame(expected, actual);
    }

    @Test
    public void findByParameters() {
        Mockito.when(dao.findByTag(ArgumentMatchers.anyString())).thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        Mockito.when(dao.findByNameOrDescription(ArgumentMatchers.anyString())).thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        List<GiftCertificate> actual = service.findByParameters(MockData.TAG_ONE.getName(), "ма", "ASC", "DESC");
        List<GiftCertificate> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        Mockito.when(dao.findAll()).thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        List<GiftCertificate> actual = service.findAll();
        List<GiftCertificate> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE);
        Assertions.assertEquals(expected, actual);
    }
}