package com.epam.esm.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.model.dao.impl.OrderDaoImpl;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class OrderServiceImplTest {

    static Order firstOrder;
    static Order secondOrder;
    static List<Order> orderList;
    static User user;
    static GiftCertificate giftCertificate;

    OrderService service;
    GiftCertificateDao giftDao;
    OrderDao orderDao;
    UserDao userDao;

    @BeforeAll
    public static void setUpData() {
        user = new User(1, "isethesal");
        giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(new BigDecimal("20.34"));
        firstOrder = new Order(1, 1, 1, new BigDecimal("20.34"), LocalDateTime.now());
        secondOrder = new Order(2, 1, 2, new BigDecimal("21.34"), LocalDateTime.now());
        orderList = new ArrayList<>();
        orderList.add(firstOrder);
        orderList.add(secondOrder);
    }

    @BeforeEach
    public void setUp() {
        orderDao = Mockito.mock(OrderDaoImpl.class);
        giftDao = Mockito.mock(GiftCertificateDaoImpl.class);
        userDao = Mockito.mock(UserDao.class);
        service = new OrderServiceImpl(orderDao, giftDao, userDao);
    }

    @Test
    void findById() {
        Mockito.when(orderDao.findById(1)).thenReturn(java.util.Optional.of(firstOrder));
        Order actual = service.findById(1);
        Order expected = firstOrder;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        Mockito.when(orderDao.findAll()).thenReturn(orderList);
        List<Order> actual = service.findAll();
        List<Order> expected = orderList;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void create() {
        Mockito.when(userDao.findById(1)).thenReturn(java.util.Optional.of(user));
        Mockito.when(giftDao.findById(1)).thenReturn(java.util.Optional.of(giftCertificate));
        Mockito.when(orderDao.create(firstOrder)).thenReturn(firstOrder);
        Order actual = service.create(firstOrder);
        Order expected = firstOrder;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findUserOrders() {
        Mockito.when(orderDao.findUserOrders(1)).thenReturn(orderList);
        List<Order> actual = service.findUserOrders(1);
        List<Order> expected = orderList;
        Assertions.assertEquals(expected, actual);
    }
}