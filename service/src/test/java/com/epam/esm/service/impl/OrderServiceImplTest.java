package com.epam.esm.service.impl;

import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.GiftRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;

class OrderServiceImplTest {

    static Order firstOrder;
    static OrderDTO firstOrderDto;
    static Order secondOrder;
    static OrderDTO secondOrderDto;
    static List<Order> orderList;
    static List<OrderDTO> orderListDto;
    static User user;
    static UserDTO userDto;
    static GiftCertificate giftCertificate;
    static GiftCertificateDTO giftCertificateDto;

    OrderService service;
    GiftRepository giftRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    @BeforeAll
    public static void setUpData() {
        giftCertificate = new GiftCertificate("test", "test description", new BigDecimal("20.40"), 12, true);
        giftCertificate.setId(1);
        giftCertificateDto = toDTO(giftCertificate);
        user = new User("isethesal");
        user.setId(1);
        userDto = toDTO(user);
        firstOrder = new Order(user, giftCertificate, new BigDecimal("20.34"), LocalDateTime.now());
        firstOrder.setId(1);
        firstOrderDto = toDTO(firstOrder);
        secondOrder = new Order(user, giftCertificate, new BigDecimal("21.34"), LocalDateTime.now());
        secondOrder.setId(2);
        secondOrderDto = toDTO(secondOrder);
        orderList = new ArrayList<>();
        orderList.add(firstOrder);
        orderList.add(secondOrder);
        orderListDto = new ArrayList<>();
        orderListDto.add(firstOrderDto);
        orderListDto.add(secondOrderDto);
    }

    @BeforeEach
    public void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        giftRepository = Mockito.mock(GiftRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        service = new OrderServiceImpl(orderRepository, giftRepository, userRepository);
    }

    @Test
    void findById() {
        Mockito.when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(firstOrder));
        OrderDTO actual = service.findById(1);
        OrderDTO expected = firstOrderDto;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        PageImpl<Order> orders = new PageImpl<>(orderList);
        Mockito.when(orderRepository.findAll(PageRequest.of(0, 100))).thenReturn(orders);
        List<OrderDTO> actual = service.findAll(100, 1);
        List<OrderDTO> expected = orderListDto;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findUserOrders() {
        Mockito.when(orderRepository.findOrdersByUserId(1L, PageRequest.of(0, 100))).thenReturn(orderList);
        List<OrderDTO> actual = service.findUserOrders(1, 100, 1);
        List<OrderDTO> expected = orderListDto;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createThrowException() {
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        Mockito.when(giftRepository.save(giftCertificate)).thenReturn(giftCertificate);
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> service.create(firstOrderDto));
    }
}