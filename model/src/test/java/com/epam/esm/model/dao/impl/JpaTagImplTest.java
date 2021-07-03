package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = {JpaTagImpl.class, SpringBootTestConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@SpringBootTest
class JpaTagImplTest {

    @Autowired
    TagDao tagDao;

    static Tag tagOne;
    static Tag tagTwo;
    static List<Tag> allTags;

    @BeforeEach
    void setUpData() {
        tagOne = new Tag(1, "snow");
        tagTwo = new Tag(2, "desert");
        allTags = new ArrayList<>();
        allTags.add(tagOne);
        allTags.add(tagTwo);
    }

    @Test
    void findById() {
        Optional<Tag> actual = tagDao.findById(1);
        Optional<Tag> expected = Optional.of(tagOne);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        List<Tag> actual = tagDao.findAll(2, 0);
        List<Tag> expected = allTags;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void create() {
        Tag tag = new Tag();
        tag.setName("страшно");
        Tag createdTag = tagDao.create(tag);
        boolean condition = createdTag.getId() > 0;
        Assertions.assertTrue(condition);
    }

    @Test
    @Transactional
    void findByName() {
        Optional<Tag> tag = tagDao.findByName("snow");
        Assertions.assertTrue(tag.isPresent());
    }
}