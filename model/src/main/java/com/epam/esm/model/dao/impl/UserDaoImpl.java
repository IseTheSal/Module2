package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcTemplate.query(SqlQueryHolder.FIND_USER, new UserMapper(), id).stream().findFirst();
    }

    @Override
    public List<User> findAll(int amount, int page) {
        return jdbcTemplate.query(SqlQueryHolder.FIND_ALL_USERS + SqlQueryHolder.PAGE_LIMIT_OFFSET,
                new UserMapper(), amount, page);
    }
}
