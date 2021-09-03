package com.epam.esm.service;

import com.epam.esm.model.dto.OrderDTO;

import java.util.List;

public interface OrderService extends CommonEntityService<OrderDTO> {

    /**
     * Create order
     *
     * @param order {@link OrderDTO}
     * @return {@link OrderDTO}
     */
    OrderDTO create(OrderDTO order);

    /**
     * Find user`s {@link OrderDTO orders}
     *
     * @param id     <code>id</code> of {@link com.epam.esm.model.entity.User User}
     * @param amount amount
     * @param page   page
     * @return <code>List</code> of {@link OrderDTO orders}
     */
    List<OrderDTO> findUserOrders(long id, int amount, int page);

    /**
     * Find user`s {@link OrderDTO order}
     *
     * @param userId  {@link com.epam.esm.model.dto.UserDTO User`s} id
     * @param orderId {@link OrderDTO Order`s} id
     * @return {@link OrderDTO}
     */
    OrderDTO findUserOrder(long userId, long orderId);
}
