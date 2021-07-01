package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


@ContextConfiguration(classes = {JpaGiftCertificateImpl.class, SpringBootTestConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@SpringBootTest
class JpaGiftCertificateImplTest {
    @Autowired
    GiftCertificateDao giftCertificateDao;

    static GiftCertificate giftCertificate;

    @BeforeEach
    void setUpData() {
        giftCertificate = new GiftCertificate(99, "Квадроцикл", "Увлекательно весело и здорово", new BigDecimal("20.04"),
                20, LocalDateTime.now(), LocalDateTime.now(), true);
    }

    @Test
    void findById() {
        Optional<GiftCertificate> actual = giftCertificateDao.findById(1);
        Assertions.assertTrue(actual.isPresent() && actual.get().getName().equals(giftCertificate.getName()));
    }

    @Test
    void findAll() {
        int actual = giftCertificateDao.findAll(3, 0).size();
        int expected = 3;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByAttributes() {
        String actual = giftCertificateDao.findByAttributes(null, "Квадроцикл", "asc", "desc", 1, 0).get(0).getName();
        String expected = giftCertificate.getName();
        Assertions.assertEquals(expected, actual);
    }
}