package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.mapper.TagMapper;
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
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag create(Tag entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueryHolder.CREATE_TAG,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            return preparedStatement;
        }, keyHolder);
        long id = (long) keyHolder.getKeys().get(SqlQueryHolder.ID_KEY_HOLDER);
        return findById(id).get();
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(SqlQueryHolder.FIND_TAG_BY_ID, new TagMapper(), id).stream().findFirst();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SqlQueryHolder.FIND_TAG_BY_NAME, new TagMapper(), name).stream().findFirst();
    }

    @Override
    public List<Tag> findAll(int amount, int page) {
        return jdbcTemplate.query(SqlQueryHolder.FIND_ALL_TAGS + SqlQueryHolder.PAGE_LIMIT_OFFSET, new TagMapper(),
                amount, page * amount);
    }

    @Override
    public boolean delete(long id) {
        return (jdbcTemplate.update(SqlQueryHolder.DELETE_TAG_BY_ID, id) > 0);
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTag() {
        return jdbcTemplate.query(SqlQueryHolder.FIND_MOST_POPULAR_TAG, new TagMapper()).stream().findFirst();
    }
}
