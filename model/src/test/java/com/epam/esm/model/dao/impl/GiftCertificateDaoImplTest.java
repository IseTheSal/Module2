//package com.epam.esm.model.dao.impl;
//
//import com.epam.esm.model.dao.GiftCertificateDao;
//import com.epam.esm.model.dao.TagDao;
//import com.epam.esm.model.entity.GiftCertificate;
//import com.epam.esm.model.entity.Tag;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.List;
//
//class GiftCertificateDaoImplTest {
//
//    private GiftCertificateDao certificateDao;
//    private TagDao tagDao;
//    private EmbeddedDatabase embeddedDatabase;
//
//    private GiftCertificate giftCertificate;
//    private List<Tag> tagList;
//
//    @BeforeEach
//    private void setUp() {
//        embeddedDatabase = new EmbeddedDatabaseBuilder()
//                .generateUniqueName(true)
//                .setType(EmbeddedDatabaseType.H2)
//                .addDefaultScripts()
//                .setScriptEncoding("UTF-8")
//                .ignoreFailedDrops(true)
//                .build();
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
//        tagDao = new TagDaoImpl(jdbcTemplate);
//
//        certificateDao = new GiftCertificateDaoImpl(jdbcTemplate, tagDao);
//        giftCertificate = new GiftCertificate();
//        giftCertificate.setId(3);
//        giftCertificate.setName("Вертолет");
//        giftCertificate.setDescription("Экстримально и страшно");
//        giftCertificate.setPrice(new BigDecimal("120.99"));
//        giftCertificate.setDuration(30);
//        Instant instant = Timestamp.valueOf("2021-06-14 00:06:20.111000").toInstant();
//        LocalDateTime zonedDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
//        giftCertificate.setCreateDate(zonedDateTime);
//        giftCertificate.setLastUpdateDate(zonedDateTime);
//
//        tagList = new ArrayList<>();
//        tagList.add(new Tag(4, "sky"));
//        tagList.add(new Tag(5, "flight"));
//        tagList.forEach(giftCertificate::addTag);
//    }
//
//    @AfterEach
//    public void close() {
//        embeddedDatabase.shutdown();
//    }
//
//    @Test
//    void create() {
//        GiftCertificate certificate = certificateDao.create(giftCertificate);
//        Assertions.assertEquals(certificate.getId(), 4);
//    }
//
//    @Test
//    void findById() {
//        GiftCertificate actual = certificateDao.findById(3).get();
//        GiftCertificate expected = giftCertificate;
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findTagsByCertificateId() {
//        List<Tag> actual = certificateDao.findTagsByCertificateId(3);
//        List<Tag> expected = tagList;
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAll() {
//        List<GiftCertificate> actual = certificateDao.findAll(0, 100);
//        Assertions.assertEquals(actual.size(), 3);
//    }
//
//    @Test
//    void deleteTrue() {
//        boolean actual = certificateDao.delete(1);
//        Assertions.assertTrue(actual);
//    }
//
//    @Test
//    void deleteFalse() {
//        boolean actual = certificateDao.delete(7);
//        Assertions.assertFalse(actual);
//    }
//
//    @Test
//    void update() {
//        GiftCertificate actual = certificateDao.update(giftCertificate);
//        GiftCertificate expected = giftCertificate;
//        Assertions.assertEquals(actual, expected);
//    }
//
//
//    @Test
//    void findByAttributes() {
//        String tagName = "flight";
//        String giftValue = "имал";
//        String dateOrderType = null;
//        String nameOrderType = "ASC";
//        int amount = 100;
//        int page = 0;
//        GiftCertificate actual = certificateDao.findByAttributes(tagName, giftValue, dateOrderType, nameOrderType, amount, page).get(0);
//        GiftCertificate expected = giftCertificate;
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findBySeveralTags() {
//        String[] tags = new String[]{"desert", "beach"};
//        int amount = 100;
//        int page = 0;
//
//        List<GiftCertificate> bySeveralTags = certificateDao.findBySeveralTags(tags, amount, page);
//        System.out.println(bySeveralTags);
//    }
//}