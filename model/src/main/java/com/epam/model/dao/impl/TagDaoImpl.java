package com.epam.model.dao.impl;

import com.epam.model.dao.TagDao;
import com.epam.model.entity.Tag;
import com.epam.model.entity.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {

    private static final String FIND_TAG_BY_ID = "SELECT id, name FROM tags WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Tag create(Tag entity) {
        return null;
    }

    @Override
    public Optional<Tag> findById(long id) {
        List<Tag> tags = jdbcTemplate.query(FIND_TAG_BY_ID, new TagMapper(), id);
        return (tags.isEmpty()) ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public boolean update(Tag entity) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
