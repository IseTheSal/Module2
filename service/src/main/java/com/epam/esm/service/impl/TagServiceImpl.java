package com.epam.esm.service.impl;

import com.epam.esm.error.exception.TagExistException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return tagDao.findById(id).orElseThrow(() -> new TagNotFoundException("id=" + id));
    }

    @Override
    public List<Tag> findAll(int amount, int page) {
        return tagDao.findAll(amount, page - 1);
    }

    @Override
    public long delete(long id) {
        if (tagDao.delete(id)) {
            return id;
        } else {
            throw new TagNotFoundException("id=" + id);
        }
    }

    @Override
    public Tag findMostWidelyUsedTag() {
        return tagDao.findMostWidelyUsedTag().orElseThrow(TagNotFoundException::new);
    }
}
