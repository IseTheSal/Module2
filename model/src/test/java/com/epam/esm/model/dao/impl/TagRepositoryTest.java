package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = {TagRepository.class, SpringBootTestConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@SpringBootTest
@Sql("/data.sql")
@Transactional
class TagRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    static Tag tagOne;
    static Tag tagTwo;
    static List<Tag> allTags;

    @BeforeEach
    void setUpData() {
        tagOne = new Tag("snow");
        tagOne.setId(1);
        tagTwo = new Tag("desert");
        tagTwo.setId(2);
        allTags = new ArrayList<>();
        allTags.add(tagOne);
        allTags.add(tagTwo);
    }

    @Test
    void findById() {
        Optional<Tag> actual = tagRepository.findById(1L);
        Optional<Tag> expected = Optional.of(tagOne);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        List<Tag> actual = tagRepository.findAll(PageRequest.of(0, 2)).toList();
        List<Tag> expected = allTags;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
        Tag tag = new Tag();
        tag.setName("страшно");
        Tag createdTag = tagRepository.save(tag);
        boolean condition = createdTag.getId() > 0;
        Assertions.assertTrue(condition);
    }

    @Test
    void findByName() {
        Optional<Tag> tag = tagRepository.findByName("snow");
        Assertions.assertTrue(tag.isPresent());
    }
}