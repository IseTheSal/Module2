package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration(classes = {JpaOrderImpl.class, SpringBootTestConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@SpringBootTest
public class JpaOrderImplTest {

    @Autowired
    OrderDao orderDao;

    static Order order;

    @BeforeEach
    void setUp() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        order = new Order(new User("isethesal"), giftCertificate, new BigDecimal("30.01"),
                LocalDateTime.now());
        order.setId(0);
    }

    @Test
    @Transactional
    void createAndFindById() {
        Order created = orderDao.create(order);
        boolean condition = created.getId() > 0;
        Assertions.assertTrue(condition);
        boolean secondCondition = orderDao.findById(created.getId()).isPresent();
        Assertions.assertTrue(secondCondition);
    }

    @Test
    @Transactional
    void findAll() {
        orderDao.create(order);
        orderDao.create(order);
        List<Order> orderList = orderDao.findAll(100, 0);
        boolean condition = (orderList.size() >= 2);
        Assertions.assertTrue(condition);
    }

    @Test
    void findUserOrders() {
        List<Order> userOrders = orderDao.findUserOrders(1, 100, 0);
        boolean condition = userOrders.size() >= 1;
        Assertions.assertTrue(condition);
    }
}
