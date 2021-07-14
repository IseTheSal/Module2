package com.epam.esm.service.impl;

import com.epam.esm.error.exception.TagExistException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.converter.ConverterDTO;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.model.dto.converter.ConverterDTO.fromDTO;
import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;

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
    @Transactional(propagation = Propagation.REQUIRED)
    public TagDTO create(TagDTO dto) {
        Tag tag = fromDTO(dto);
        String name = tag.getName();
        if (!TagValidator.isNameValid(name)) {
            throw new ValidationException(messageSource.getMessage("error.tag.validation.name", new Object[]{name},
                    LocaleContextHolder.getLocale()));
        }
        tagDao.findByName(name).ifPresent(tag1 -> {
            throw new TagExistException(name);
        });
        return toDTO(tagDao.create(tag));
    }

    @Override
    public TagDTO findById(long id) {
        return toDTO(tagDao.findById(id).orElseThrow(() -> new TagNotFoundException("id=" + id)));
    }

    @Override
    public List<TagDTO> findAll(int amount, int page) {
        return tagDao.findAll(amount, page - 1).stream().map(ConverterDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long delete(long id) {
        if (tagDao.delete(id)) {
            return id;
        } else {
            throw new TagNotFoundException("id=" + id);
        }
    }

    @Override
    public TagDTO findMostWidelyUsedTag() {
        return toDTO(tagDao.findMostWidelyUsedTag().orElseThrow(TagNotFoundException::new));
    }
}
