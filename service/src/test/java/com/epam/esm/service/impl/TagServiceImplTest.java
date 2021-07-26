//package com.epam.esm.service.impl;
//
//import com.epam.esm.error.exception.TagExistException;
//import com.epam.esm.error.exception.TagNotFoundException;
//import com.epam.esm.error.exception.ValidationException;
//import com.epam.esm.model.dao.TagDao;
//import com.epam.esm.model.dao.impl.JpaTagImpl;
//import com.epam.esm.model.dto.TagDTO;
//import com.epam.esm.service.TagService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.context.MessageSource;
//
//import java.util.Collections;
//import java.util.List;
//
//class TagServiceImplTest {
//
//    TagService service;
//    TagDao dao;
//    MessageSource messageSource;
//
//    @BeforeEach
//    public void setUp() {
//        dao = Mockito.mock(JpaTagImpl.class);
//        messageSource = Mockito.mock(MessageSource.class);
//        service = new TagServiceImpl(dao, userRepository, messageSource);
//    }
//
//    @Test
//    void create() {
//        Mockito.when(dao.findByName(ArgumentMatchers.anyString()))
//                .thenReturn(java.util.Optional.empty());
//        Mockito.when(dao.create(MockData.TAG_ONE)).thenReturn(MockData.TAG_ONE);
//        TagDTO actual = service.create(MockData.TAG_ONE_DTO);
//        TagDTO expected = MockData.TAG_ONE_DTO;
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void createThrowTagExistException() {
//        Mockito.when(dao.create(MockData.TAG_ONE)).thenThrow(new TagExistException("name"));
//        Assertions.assertThrows(TagExistException.class, () -> service.create(MockData.TAG_ONE_DTO));
//    }
//
//    @Test
//    void createThrowValidationException() {
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setName("inco$$e^t");
//        Assertions.assertThrows(ValidationException.class, () -> service.create(tagDTO));
//    }
//
//    @Test
//    void findById() {
//        Mockito.when(dao.findById(ArgumentMatchers.anyLong()))
//                .thenReturn(java.util.Optional.ofNullable(MockData.TAG_ONE));
//        TagDTO actual = service.findById(1);
//        TagDTO expected = MockData.TAG_ONE_DTO;
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAll() {
//        Mockito.when(dao.findAll(100, 0)).thenReturn(Collections.singletonList(MockData.TAG_ONE));
//        List<TagDTO> actual = service.findAll(100, 1);
//        List<TagDTO> expected = Collections.singletonList(MockData.TAG_ONE_DTO);
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void delete() {
//        Mockito.when(dao.delete(ArgumentMatchers.anyLong())).thenReturn(true);
//        long actual = service.delete(1);
//        long expected = 1;
//        Assertions.assertSame(expected, actual);
//    }
//
//    @Test
//    void deleteThrownException() {
//        Mockito.when(dao.delete(ArgumentMatchers.anyLong())).thenReturn(false);
//        Assertions.assertThrows(TagNotFoundException.class, () -> service.delete(8));
//    }
//
//    @Test
//    void findMostWidely() {
//        Mockito.when(dao.findMostWidelyUsedTag()).thenReturn(java.util.Optional.ofNullable(MockData.TAG_ONE));
//        TagDTO actual = service.findMostWidelyUsedTag();
//        TagDTO expected = MockData.TAG_ONE_DTO;
//        Assertions.assertEquals(expected, actual);
//    }
//}