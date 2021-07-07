package com.epam.esm.service;

import com.epam.esm.model.entity.Order;

import java.util.List;

public interface OrderService extends CommonEntityService<Order> {

    /**
     * Create order
     *
     * @param order {@link Order}
     * @return {@link Order}
     */
    Order create(Order order);

    /**
     * Find user`s {@link Order orders}
     *
     * @param id     <code>id</code> of {@link com.epam.esm.model.entity.User User}
     * @param amount amount
     * @param page   page
     * @return <code>List</code> of {@link Order orders}
     */
    List<Order> findUserOrders(long id, int amount, int page);
}
