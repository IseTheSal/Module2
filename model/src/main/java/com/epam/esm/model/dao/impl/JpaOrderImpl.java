package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaOrderImpl implements OrderDao {



    @Override
    public Optional<Order> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll(int amount, int page) {
        return null;
    }

    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    public List<Order> findUserOrders(long userId, int amount, int page) {
        return null;
    }
}
