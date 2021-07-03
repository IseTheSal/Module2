package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Order> findById(long id) {
        return jdbcTemplate.query(SqlQueryHolder.FIND_ORDER, new OrderMapper(), id).stream().findFirst();
    }

    @Override
    public List<Order> findAll(int amount, int page) {
        return jdbcTemplate.query(SqlQueryHolder.FIND_ALL_ORDERS + SqlQueryHolder.PAGE_LIMIT_OFFSET,
                new OrderMapper(), amount, page);
    }

    @Override
    public Order create(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SqlQueryHolder.CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, order.getUserId());
            ps.setLong(2, order.getCertificateId());
            ps.setBigDecimal(3, order.getPrice());
            return ps;
        }, keyHolder);
        long id = (long) keyHolder.getKeys().get(SqlQueryHolder.ID_KEY_HOLDER);
        return findById(id).get();
    }

    @Override
    public List<Order> findUserOrders(long userId, int amount, int page) {
        return jdbcTemplate.query(SqlQueryHolder.FIND_USER_ORDERS + SqlQueryHolder.PAGE_LIMIT_OFFSET,
                new OrderMapper(), userId, amount, page * amount);
    }
}
