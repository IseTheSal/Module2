package com.epam.esm.model.entity.mapper;

import com.epam.esm.model.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(TAG_ID);
        String name = rs.getString(TAG_NAME);
        return new Tag(id, name);
    }
}
