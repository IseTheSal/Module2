package com.epam.esm.service.impl;

import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.repository.GiftRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.security.PermissionChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class CertificateServiceImplTest {

    GiftCertificateService service;
    GiftRepository giftRepository;
    PermissionChecker permissionChecker;
    MessageSource messageSource;
    TagRepository tagRepository;

    @BeforeEach
    public void setUp() {
        permissionChecker = Mockito.mock(PermissionChecker.class);
        giftRepository = Mockito.mock(GiftRepository.class);
        messageSource = Mockito.mock(MessageSource.class);
        tagRepository = Mockito.mock(TagRepository.class);
        service = new GiftCertificateServiceImpl(giftRepository, tagRepository, messageSource, permissionChecker);
    }

    @Test
    void findAll() {
        MockData.GIFT_CERTIFICATE.setForSale(false);
        PageImpl<GiftCertificate> giftCertificates = new PageImpl<>(Collections.singletonList(MockData.GIFT_CERTIFICATE));
        when(giftRepository.findAll(PageRequest.of(0, 100))).thenReturn(giftCertificates);
        List<GiftCertificateDTO> actual = service.findAll(100, 1);
        List<GiftCertificateDTO> expected = Collections.singletonList(MockData.GIFT_CERTIFICATE_DTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByParametersTagNotException() {
        when(tagRepository.findByName(MockData.TAG_ONE.getName())).thenThrow(new TagNotFoundException());
        Assertions.assertThrows(TagNotFoundException.class, () -> service
                .findByParameters(Collections.singletonList(MockData.TAG_ONE.getName()), "катан", null, null, 100, 1));
    }

    @Test
    void findByParametersValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> service
                .findByParameters(Collections.singletonList(MockData.TAG_ONE.getName()), "катан", "ДЕСК", null, 100, 1));
    }

    @Test
    void findById() {
        when(giftRepository.findById(1L)).thenReturn(Optional.ofNullable(MockData.GIFT_CERTIFICATE));
        GiftCertificateDTO actual = service.findById(1);
        GiftCertificateDTO expected = MockData.GIFT_CERTIFICATE_DTO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
        MockData.GIFT_CERTIFICATE.setForSale(false);
        when(giftRepository.save(MockData.GIFT_CERTIFICATE)).thenReturn(MockData.GIFT_CERTIFICATE);
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        GiftCertificateDTO actual = service.create(MockData.GIFT_CERTIFICATE_DTO);
        GiftCertificateDTO expected = MockData.GIFT_CERTIFICATE_DTO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        when(giftRepository.findById(1L)).thenReturn(Optional.of(MockData.GIFT_CERTIFICATE));
        when(giftRepository.saveAndFlush(ArgumentMatchers.any())).thenReturn(MockData.UPDATED_GIFT_CERTIFICATE);
        GiftCertificateDTO actual = service.update(MockData.UPDATED_GIFT_CERTIFICATE_DTO);
        GiftCertificateDTO expected = MockData.UPDATED_GIFT_CERTIFICATE_DTO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        when(giftRepository.findById(1L)).thenReturn(Optional.of(MockData.GIFT_CERTIFICATE));
        long actual = service.delete(1);
        long expected = 1;
        Assertions.assertSame(expected, actual);
    }

    @Test
    void deleteThrowException() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        when(giftRepository.findById(9L)).thenReturn(Optional.empty());
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> service.delete(9));
    }

    @Test
    void removeFromSales() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        when(giftRepository.findById(1L)).thenReturn(Optional.of(MockData.GIFT_CERTIFICATE));
        when(giftRepository.save(MockData.GIFT_CERTIFICATE)).thenReturn(MockData.GIFT_CERTIFICATE);
        long actual = service.removeFromSale(MockData.GIFT_CERTIFICATE_DTO.getId());
        long expected = MockData.GIFT_CERTIFICATE_DTO.getId();
        Assertions.assertEquals(expected, actual);
    }
}