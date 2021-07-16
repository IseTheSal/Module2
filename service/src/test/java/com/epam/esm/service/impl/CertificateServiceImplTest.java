package com.epam.esm.service.impl;

import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.impl.JpaGiftCertificateImpl;
import com.epam.esm.model.dao.impl.JpaTagImpl;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    void findAll() {
        Mockito.when(dao.findAll(100, 0)).thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        List<GiftCertificateDTO> actual = service.findAll(100, 1);
        List<GiftCertificateDTO> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE_DTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByParameters() {
        Mockito.when(dao.findByAttributes(new String[]{MockData.TAG_ONE.getName()}, "катание", null, null, 100, 0))
                .thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        Mockito.when(tagDao.findByName(MockData.TAG_ONE.getName())).thenReturn(Optional.of(MockData.TAG_ONE));
        List<GiftCertificateDTO> actual = service.findByParameters(Collections.singletonList(MockData.TAG_ONE.getName()),
                "катание", null, null, 100, 1);
        List<GiftCertificateDTO> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE_DTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByParametersTagNotException() {
        Mockito.when(tagDao.findByName(MockData.TAG_ONE.getName())).thenThrow(new TagNotFoundException());
        Assertions.assertThrows(TagNotFoundException.class, () -> service
                .findByParameters(Collections.singletonList(MockData.TAG_ONE.getName()), "катан", null, null, 100, 1));
    }

    @Test
    void findByParametersValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> service
                .findByParameters(Collections.singletonList(MockData.TAG_ONE.getName()), "катан", "ДЕСК", null, 100, 1));
    }

    @Test
    void findByParametersTagNull() {
        Mockito.when(dao.findByAttributes(new String[0], "катание", null, null, 100, 0))
                .thenReturn(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        Mockito.when(tagDao.findByName(MockData.TAG_ONE.getName())).thenReturn(Optional.of(MockData.TAG_ONE));
        List<GiftCertificateDTO> actual = service.findByParameters(null, "катание", null, null, 100, 1);
        List<GiftCertificateDTO> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE_DTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findById() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.ofNullable(MockData.GIFT_CERTIFICATE));
        GiftCertificateDTO actual = service.findById(1);
        GiftCertificateDTO expected = MockData.GIFT_CERTIFICATE_DTO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
        Mockito.when(dao.create(MockData.GIFT_CERTIFICATE)).thenReturn(MockData.GIFT_CERTIFICATE);
        GiftCertificateDTO actual = service.create(MockData.GIFT_CERTIFICATE_DTO);
        GiftCertificateDTO expected = MockData.GIFT_CERTIFICATE_DTO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(MockData.GIFT_CERTIFICATE));
        Mockito.when(dao.update(ArgumentMatchers.any())).thenReturn(MockData.UPDATED_GIFT_CERTIFICATE);
        GiftCertificateDTO actual = service.update(MockData.UPDATED_GIFT_CERTIFICATE_DTO);
        GiftCertificateDTO expected = MockData.UPDATED_GIFT_CERTIFICATE_DTO;
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
    void removeFromSales() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(MockData.GIFT_CERTIFICATE));
        Mockito.when(dao.update(MockData.GIFT_CERTIFICATE)).thenReturn(MockData.GIFT_CERTIFICATE);
        long actual = service.removeFromSale(MockData.GIFT_CERTIFICATE_DTO.getId());
        long expected = MockData.GIFT_CERTIFICATE_DTO.getId();
        Assertions.assertEquals(expected, actual);
    }
}