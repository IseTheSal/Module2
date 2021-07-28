package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.OrderRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration(classes = {OrderRepository.class, SpringBootTestConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@SpringBootTest
@Sql("/data.sql")
@Transactional
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

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
    void createAndFindById() {
        Order created = orderRepository.save(order);
        boolean condition = created.getId() > 0;
        Assertions.assertTrue(condition);
        boolean secondCondition = orderRepository.findById(created.getId()).isPresent();
        Assertions.assertTrue(secondCondition);
    }

    @Test
    void findAll() {
        List<Order> orderList = orderRepository.findAll(PageRequest.of(0, 100)).toList();
        boolean condition = (orderList.size() >= 2);
        Assertions.assertTrue(condition);
    }

    @Test
    void findUserOrders() {
        List<Order> userOrders = orderRepository.findOrdersByUserId(1, PageRequest.of(0, 100));
        boolean condition = userOrders.size() >= 1;
        Assertions.assertTrue(condition);
    }
}
