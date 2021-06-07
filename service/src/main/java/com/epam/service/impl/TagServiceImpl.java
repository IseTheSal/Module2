package com.epam.service.impl;

import com.epam.model.dao.TagDao;
import com.epam.model.entity.Tag;
import com.epam.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag create(Tag entity) {
        return null;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return tagDao.findById(id);
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
