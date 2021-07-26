//package com.epam.esm.model.dao.impl;
//
//import com.epam.esm.model.dao.GiftCertificateDao;
//import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
//import com.epam.esm.model.entity.GiftCertificate;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//
//@ContextConfiguration(classes = {JpaGiftCertificateImpl.class, SpringBootTestConfiguration.class},
//        loader = AnnotationConfigContextLoader.class)
//@DirtiesContext
//@SpringBootTest
//class JpaGiftCertificateImplTest {
//
//    @Autowired
//    GiftCertificateDao giftCertificateDao;
//
//    static GiftCertificate giftCertificate;
//
//    @BeforeEach
//    void setUpData() {
//        giftCertificate = new GiftCertificate("Квадроцикл", "Увлекательно весело и здорово", new BigDecimal("20.04"),
//                20, true);
//        giftCertificate.setId(99);
//    }
//
//    @Test
//    void findById() {
//        Optional<GiftCertificate> actual = giftCertificateDao.findById(1);
//        Assertions.assertTrue(actual.isPresent() && actual.get().getName().equals(giftCertificate.getName()));
//    }
//
//    @Test
//    void findAll() {
//        int actual = giftCertificateDao.findAll(3, 0).size();
//        int expected = 3;
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findByAttributes() {
//        String actual = giftCertificateDao.findByAttributes(new String[0], "Квадроцикл", "asc", "desc", 1, 0).get(0).getName();
//        String expected = giftCertificate.getName();
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    @Transactional
//    void delete() {
//        boolean delete = giftCertificateDao.delete(1);
//        Assertions.assertTrue(delete);
//    }
//
//    @Test
//    @Transactional
//    void create() {
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setName("Самолёт");
//        giftCertificate.setDescription(" 1 Самолёт");
//        giftCertificate.setPrice(new BigDecimal("20.1"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setLastUpdateDate(LocalDateTime.now());
//        giftCertificate.setCreateDate(LocalDateTime.now());
//        GiftCertificate created = giftCertificateDao.create(giftCertificate);
//        boolean condition = created.getId() > 0;
//        Assertions.assertTrue(condition);
//    }
//
//    @Test
//    @Transactional
//    void update() {
//        GiftCertificate giftCertificate = giftCertificateDao.findById(1).get();
//        giftCertificate.setForSale(false);
//        giftCertificateDao.update(giftCertificate);
//        boolean condition = giftCertificateDao.findById(1).get().isForSale();
//        Assertions.assertFalse(condition);
//    }
//}