package com.epam.service.impl;

import com.epam.exception.RestErrorStatusCode;
import com.epam.exception.TagExistException;
import com.epam.exception.TagNotFoundException;
import com.epam.exception.ValidationException;
import com.epam.model.dao.TagDao;
import com.epam.model.entity.Tag;
import com.epam.service.TagService;
import com.epam.validator.TagValidator;
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
            if (!tagDao.isNameExist(name)) {
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
            throw new TagNotFoundException("tag with id (" + id + ") does not exist", RestErrorStatusCode.TAG_NOT_FOUND);
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
            throw new TagNotFoundException("tag with id (" + id + ") does not exist", RestErrorStatusCode.TAG_NOT_FOUND);
        }
    }

    private long parseId(String id) {
        if (!TagValidator.isIdValid(id)) {
            throw new ValidationException("Tag id {" + id + "} is not valid", RestErrorStatusCode.VALIDATION_ERROR);
        }
        return Long.parseLong(id);
    }
}
