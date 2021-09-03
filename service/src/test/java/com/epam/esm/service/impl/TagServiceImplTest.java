package com.epam.esm.service.impl;

import com.epam.esm.error.exception.TagExistException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.security.PermissionChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class TagServiceImplTest {

    TagService service;
    TagRepository tagRepository;
    UserRepository userRepository;
    MessageSource messageSource;
    PermissionChecker permissionChecker;

    @BeforeEach
    public void setUp() {
        tagRepository = Mockito.mock(TagRepository.class);
        messageSource = Mockito.mock(MessageSource.class);
        userRepository = Mockito.mock(UserRepository.class);
        permissionChecker = Mockito.mock(PermissionChecker.class);
        service = new TagServiceImpl(tagRepository, userRepository, messageSource, permissionChecker);
    }

    @Test
    void create() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        when(tagRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(java.util.Optional.empty());
        when(tagRepository.save(MockData.TAG_ONE)).thenReturn(MockData.TAG_ONE);
        TagDTO actual = service.create(MockData.TAG_ONE_DTO);
        TagDTO expected = MockData.TAG_ONE_DTO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createThrowTagExistException() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        when(tagRepository.save(MockData.TAG_ONE)).thenThrow(new TagExistException("name"));
        Assertions.assertThrows(TagExistException.class, () -> service.create(MockData.TAG_ONE_DTO));
    }

    @Test
    void createThrowValidationException() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("inco$$e^t");
        Assertions.assertThrows(ValidationException.class, () -> service.create(tagDTO));
    }

    @Test
    void findById() {
        when(tagRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.ofNullable(MockData.TAG_ONE));
        TagDTO actual = service.findById(1);
        TagDTO expected = MockData.TAG_ONE_DTO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        when(tagRepository.findAll(PageRequest.of(0, 100))).thenReturn(new PageImpl<>(Collections.singletonList(MockData.TAG_ONE)));
        List<TagDTO> actual = service.findAll(100, 1);
        List<TagDTO> expected = Collections.singletonList(MockData.TAG_ONE_DTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        when(tagRepository.findById(1L)).thenReturn(Optional.ofNullable(MockData.TAG_ONE));
        long actual = service.delete(1);
        long expected = 1;
        Assertions.assertSame(expected, actual);
    }

    @Test
    void deleteThrownException() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        when(tagRepository.findById(8L)).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class, () -> service.delete(8));
    }

    @Test
    void findMostWidely() {
        when(tagRepository.findMostWidelyUsedTag(1L, PageRequest.of(0, 1)))
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(TagNotFoundException.class, () -> service.findMostWidelyUsedTag());
    }
}