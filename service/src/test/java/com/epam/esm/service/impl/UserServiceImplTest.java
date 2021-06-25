package com.epam.esm.service.impl;

import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.impl.UserDaoImpl;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UserServiceImplTest {

    static User firstUser;
    static User secondUser;
    static List<User> userList;

    UserDao dao;
    UserService service;

    @BeforeAll
    public static void setUpData() {
        firstUser = new User(1, "isethesal");
        secondUser = new User(2, "ileathehunter");
        userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);
    }

    @BeforeEach
    public void setUp() {
        dao = Mockito.mock(UserDaoImpl.class);
        service = new UserServiceImpl(dao);
    }

    @Test
    void findById() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(firstUser));
        User actual = service.findById(1);
        User expected = firstUser;
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
        List<User> actual = service.findAll(1000, 0);
        List<User> expected = userList;
        Assertions.assertEquals(expected, actual);
    }
}