package com.epam.esm.service.impl;

import com.epam.esm.exception.TagExistException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final MessageSource messageSource;

    @Autowired
    public TagServiceImpl(TagDao tagDao, MessageSource messageSource) {
        this.tagDao = tagDao;
        this.messageSource = messageSource;
    }

    @Override
    public Tag create(Tag tag) {
        String name = tag.getName();
        if (TagValidator.isNameValid(name)) {
            tagDao.findByName(name).ifPresent(tag1 -> {
                throw new TagExistException(name);
            });
            return tagDao.create(tag);
        } else {
            throw new ValidationException(messageSource.getMessage("error.tag.validation.name", new Object[]{name},
                    LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Tag findById(long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        return optionalTag.orElseThrow(() -> new TagNotFoundException(id));
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public long delete(long id) {
        if (tagDao.delete(id)) {
            return id;
        } else {
            throw new TagNotFoundException(id);
        }
    }
}
