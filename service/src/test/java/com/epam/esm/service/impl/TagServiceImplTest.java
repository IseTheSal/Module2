package com.epam.esm.service.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.impl.TagDaoImpl;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

class TagServiceImplTest {

    TagService service;
    TagDao dao;

    @BeforeEach
    public void setUp() {
        dao = Mockito.mock(TagDaoImpl.class);
        service = new TagServiceImpl(dao);
    }

    @Test
    void create() {
        Mockito.when(dao.findByName(ArgumentMatchers.anyString()))
                .thenReturn(java.util.Optional.empty());
        Mockito.when(dao.create(MockData.TAG_ONE)).thenReturn(MockData.TAG_ONE);
        Tag actual = service.create(MockData.TAG_ONE);
        Tag expected = MockData.TAG_ONE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findById() {
        Mockito.when(dao.findById(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.ofNullable(MockData.TAG_ONE));
        Tag actual = service.findById(String.valueOf(1));
        Tag expected = MockData.TAG_ONE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        Mockito.when(dao.findAll()).thenReturn(Collections.singletonList(MockData.TAG_ONE));
        List<Tag> actual = service.findAll();
        List<Tag> expected = Collections.singletonList(MockData.TAG_ONE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        Mockito.when(dao.delete(ArgumentMatchers.anyLong())).thenReturn(true);
        long actual = service.delete(String.valueOf(1));
        long expected = 1;
        Assertions.assertSame(expected, actual);

    }
}