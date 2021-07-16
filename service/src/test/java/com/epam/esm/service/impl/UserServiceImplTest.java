package com.epam.esm.service.impl;

import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.impl.JpaUserImpl;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;

class UserServiceImplTest {

    static User firstUser;
    static UserDTO firstUserDto;
    static User secondUser;
    static UserDTO secondUserDto;
    static List<User> userList;
    static List<UserDTO> userListDto;
    static PasswordEncoder passwordEncoder;

    UserDao dao;
    UserService service;

    @BeforeAll
    public static void setUpData() {
        firstUser = new User("isethesal");
        firstUser.setId(1);
        firstUserDto = toDTO(firstUser);
        secondUser = new User("ileathehunter");
        secondUser.setId(2);
        secondUserDto = toDTO(secondUser);
        userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);
        userListDto = new ArrayList<>();
        userListDto.add(firstUserDto);
        userListDto.add(secondUserDto);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @BeforeEach
    public void setUp() {
        dao = Mockito.mock(JpaUserImpl.class);
        service = new UserServiceImpl(dao, null, passwordEncoder);
    }

    @Test
    void findById() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(firstUser));
        UserDTO actual = service.findById(1);
        UserDTO expected = firstUserDto;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdThrownException() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void findAll() {
        Mockito.when(dao.findAll(100, 0)).thenReturn(userList);
        List<UserDTO> actual = service.findAll(100, 1);
        List<UserDTO> expected = userListDto;
        Assertions.assertEquals(expected, actual);
    }
}