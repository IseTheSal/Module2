package com.epam.esm.service;

import com.epam.esm.model.entity.Order;

import java.util.List;

public interface OrderService extends CommonEntityService<Order> {

    Order create(Order order);

    List<Order> findUserOrders(long id);
}
