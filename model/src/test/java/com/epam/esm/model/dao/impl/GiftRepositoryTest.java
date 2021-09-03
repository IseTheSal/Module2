package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.repository.GiftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@ContextConfiguration(classes = {GiftRepository.class, SpringBootTestConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@SpringBootTest
@Sql("/data.sql")
@Transactional
class GiftRepositoryTest {

    @Autowired
    GiftRepository giftRepository;

    static GiftCertificate giftCertificate;

    @BeforeEach
    void setUpData() {
        giftCertificate = new GiftCertificate("Квадроцикл", "Увлекательно весело и здорово", new BigDecimal("20.04"),
                20, true);
        giftCertificate.setId(99);
    }

    @Test
    void findAll() {
        int actual = giftRepository.findAll(PageRequest.of(0, 3)).toList().size();
        int expected = 3;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Самолёт");
        giftCertificate.setDescription(" 1 Самолёт");
        giftCertificate.setPrice(new BigDecimal("20.1"));
        giftCertificate.setDuration(1);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificate.setCreateDate(LocalDateTime.now());
        GiftCertificate created = giftRepository.saveAndFlush(giftCertificate);
        boolean condition = created.getId() > 0;
        Assertions.assertTrue(condition);
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = giftRepository.findById(1L).get();
        giftCertificate.setForSale(false);
        giftRepository.save(giftCertificate);
        boolean condition = giftRepository.findById(1L).get().isForSale();
        Assertions.assertFalse(condition);
    }
}