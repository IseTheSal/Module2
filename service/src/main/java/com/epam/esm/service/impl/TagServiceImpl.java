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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    //fixme ask if it`s correct
    private MessageSource messageSource;
    private final Locale locale = new Locale("ru", "RU");

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Autowired
    private void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Tag create(Tag tag) {
        String name = tag.getName();
        if (TagValidator.isNameValid(name)) {
            if (!tagDao.findByName(name).isPresent()) {
                return tagDao.create(tag);
            } else {
                throw new TagExistException(messageSource.getMessage("error.tag.exist.name",
                        new Object[]{name},
                        locale), RestErrorStatusCode.TAG_EXIST);
            }
        } else {
            throw new ValidationException(messageSource.getMessage("error.validation.name",
                    new Object[]{name},
                    locale),
                    RestErrorStatusCode.VALIDATION_ERROR);
        }
    }

    @Override
    public Tag findById(String id) {
        long idValue = parseId(id);
        Optional<Tag> optionalTag = tagDao.findById(idValue);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        } else {
            throw new TagNotFoundException(messageSource.getMessage("error.tag.not.found",
                    new Object[]{id},
                    locale),
                    RestErrorStatusCode.ENTITY_NOT_FOUND);
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
            throw new TagNotFoundException(messageSource.getMessage("error.tag.not.found",
                    new Object[]{id},
                    locale), RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
    }
}
