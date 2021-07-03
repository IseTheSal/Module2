package com.epam.esm.model.entity.mapper;

import com.epam.esm.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    private static final String USER_ID = "id";
    private static final String USER_LOGIN = "login";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(USER_ID);
        String login = rs.getString(USER_LOGIN);
        return new User(id, login);
    }
}
