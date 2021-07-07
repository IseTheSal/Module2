package com.epam.esm.service.impl;

import com.epam.esm.error.exception.TagExistException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.impl.JpaTagImpl;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;

import java.util.Collections;
import java.util.List;

class TagServiceImplTest {

    TagService service;
    TagDao dao;
    MessageSource messageSource;

    @BeforeEach
    public void setUp() {
        dao = Mockito.mock(JpaTagImpl.class);
        messageSource = Mockito.mock(MessageSource.class);
        service = new TagServiceImpl(dao, messageSource);
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
    void createThrowTagExistException() {
        Mockito.when(dao.create(MockData.TAG_ONE)).thenThrow(new TagExistException("name"));
        Assertions.assertThrows(TagExistException.class, () -> service.create(MockData.TAG_ONE));
    }

    @Test
    void createThrowValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> service.create(new Tag("incorr$erct")));
    }

    @Test
    void findById() {
        Mockito.when(dao.findById(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.ofNullable(MockData.TAG_ONE));
        Tag actual = service.findById(1);
        Tag expected = MockData.TAG_ONE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        Mockito.when(dao.findAll(100, 0)).thenReturn(Collections.singletonList(MockData.TAG_ONE));
        List<Tag> actual = service.findAll(100, 1);
        List<Tag> expected = Collections.singletonList(MockData.TAG_ONE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        Mockito.when(dao.delete(ArgumentMatchers.anyLong())).thenReturn(true);
        long actual = service.delete(1);
        long expected = 1;
        Assertions.assertSame(expected, actual);
    }

    @Test
    void deleteThrownException() {
        Mockito.when(dao.delete(ArgumentMatchers.anyLong())).thenReturn(false);
        Assertions.assertThrows(TagNotFoundException.class, () -> service.delete(8));
    }

    @Test
    void findMostWidely() {
        Mockito.when(dao.findMostWidelyUsedTag()).thenReturn(java.util.Optional.ofNullable(MockData.TAG_ONE));
        Tag actual = service.findMostWidelyUsedTag();
        Tag expected = MockData.TAG_ONE;
        Assertions.assertEquals(expected, actual);
    }
}