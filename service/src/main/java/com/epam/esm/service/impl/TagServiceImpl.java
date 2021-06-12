package com.epam.esm.service.impl;

import com.epam.esm.exception.RestErrorStatusCode;
import com.epam.esm.exception.TagExistException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
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
    public Tag create(Tag tag) {
        String name = tag.getName();
        if (TagValidator.isNameValid(name)) {
            if (!tagDao.findByName(name).isPresent()) {
                return tagDao.create(tag);
            } else {
                throw new TagExistException("Tag already" + name + "exists", RestErrorStatusCode.TAG_EXIST);
            }
        } else {
            throw new ValidationException("Tag name {" + name + "} is not valid", RestErrorStatusCode.VALIDATION_ERROR);
        }
    }

    @Override
    public Tag findById(String id) {
        long idValue = parseId(id);
        Optional<Tag> optionalTag = tagDao.findById(idValue);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        } else {
            throw new TagNotFoundException("tag with id (" + id + ") does not exist", RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public long delete(String id) {
        long idValue = parseId(id);
        if (tagDao.delete(idValue)) {
            return idValue;
        } else {
            throw new TagNotFoundException("tag with id (" + id + ") does not exist", RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
    }
}
