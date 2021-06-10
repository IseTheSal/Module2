package com.epam.model.dao.impl;

import com.epam.model.dao.TagDao;
import com.epam.model.entity.Tag;
import com.epam.model.entity.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {

    private static final String FIND_TAG_BY_ID = "SELECT id, name FROM tags WHERE id = ?";
    private static final String FIND_TAG_BY_NAME = "SELECT id, name FROM tags WHERE name = ?";
    private static final String FIND_ALL_TAGS = "SELECT id, name FROM tags";
    private static final String DELETE_TAG_BY_ID = "DELETE FROM tags where id = ?";
    private static final String CREATE_TAG = "INSERT INTO tags(id, name) VALUES(default, ?)";
    private static final String ID_KEY_HOLDER = "id";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag create(Tag entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            return preparedStatement;
        }, keyHolder);
        long id = (long) keyHolder.getKeys().get(ID_KEY_HOLDER);
        return findById(id).get();
    }

    @Override
    public Optional<Tag> findById(long id) {
        List<Tag> tags = jdbcTemplate.query(FIND_TAG_BY_ID, new TagMapper(), id);
        return (tags.isEmpty()) ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS, new TagMapper());
    }

    @Override
    public boolean delete(long id) {
        return (jdbcTemplate.update(DELETE_TAG_BY_ID, id) > 0);
    }

    @Override
    public boolean isNameExist(String name) {
        List<Tag> tagList = jdbcTemplate.query(FIND_TAG_BY_NAME, new TagMapper(), name);
        return (!tagList.isEmpty());
    }
}
