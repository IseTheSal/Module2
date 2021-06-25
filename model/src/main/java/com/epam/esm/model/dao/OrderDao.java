package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Order;

import java.util.List;

/**
 * Interface extends {@link Order} database functionality
 *
 * @author Illia Aheyeu
 */
public interface OrderDao extends CommonDao<Order> {

    /**
     * Create new {@link Order}
     *
     * @param order {@link Order}
     * @return Created {@link Order}
     */
    Order create(Order order);

    /**
     * Find {@link com.epam.esm.model.entity.User User`s} {@link Order Orders} by his id
     *
     * @param userId {@link com.epam.esm.model.entity.User User`s} id
     * @param amount Amount of values in page
     * @param page   Page number
     * @return <code>List</code> of {@link Order Orders}
     */
    List<Order> findUserOrders(long userId, int amount, int page);
}
