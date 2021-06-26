package com.epam.esm.service.impl;

import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String UPDATE_OPTION = "UPDATE";
    private static final String CREATE_OPTION = "CREATE";
    private static final String ASC_SORT = "ASC";
    private static final String DESC_SORT = "DESC";

    private final GiftCertificateDao giftCertificateDao;
    private final MessageSource messageSource;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, MessageSource messageSource) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {
        checkCertificateValid(giftCertificate, CREATE_OPTION);
        checkTagsValid(giftCertificate.getTags());
        return giftCertificateDao.create(giftCertificate);
    }

    private void checkTagsValid(Set<Tag> tagSet) {
        StringBuilder exceptionValidMessage = new StringBuilder();
        for (Tag tag : tagSet) {
            String name = tag.getName();
            if (!TagValidator.isNameValid(name)) {
                exceptionValidMessage.append(messageSource.getMessage("error.tag.validation.name",
                        new Object[]{name}, LocaleContextHolder.getLocale()));
            }
        }
        String message = exceptionValidMessage.toString();
        if (!message.isEmpty()) {
            throw new ValidationException(message);
        }
    }

    private void checkCertificateValid(GiftCertificate giftCertificate, String option) {
        StringBuilder exceptionValidMessage = new StringBuilder();
        String name = giftCertificate.getName();
        if (((name != null) && !GiftCertificateValidator.isNameValid(name) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isNameValid(name) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append(messageSource.getMessage("error.gift.validation.name",
                    new Object[]{name},
                    LocaleContextHolder.getLocale())).append("\n");
        }
        String description = giftCertificate.getDescription();
        if (((description != null)
                && !GiftCertificateValidator.isDescriptionValid(description) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isDescriptionValid(description) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append(messageSource.getMessage("error.gift.validation.description",
                    new Object[]{description},
                    LocaleContextHolder.getLocale())).append("\n");
        }
        BigDecimal price = giftCertificate.getPrice();
        if (((price != null) && !GiftCertificateValidator.isPriceValid(price) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isPriceValid(price) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append(messageSource.getMessage("error.gift.validation.price",
                    new Object[]{price},
                    LocaleContextHolder.getLocale())).append("\n");
        }
        Integer duration = giftCertificate.getDuration();
        if (((duration != null) && !GiftCertificateValidator.isDurationValid(duration) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isDurationValid(duration) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append(messageSource.getMessage("error.gift.validation.duration",
                    new Object[]{duration},
                    LocaleContextHolder.getLocale())).append("\n");
        }
        String message = exceptionValidMessage.toString();
        if (!message.isEmpty()) {
            throw new ValidationException(message);
        }
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate giftCertificate) {
        long id = giftCertificate.getId();
        giftCertificateDao.findById(id).orElseThrow(() -> new GiftCertificateNotFoundException(id));
        checkCertificateValid(giftCertificate, UPDATE_OPTION);
        checkTagsValid(giftCertificate.getTags());
        return giftCertificateDao.update(giftCertificate);
    }

    @Override
    public GiftCertificate findById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        return optionalGiftCertificate.orElseThrow(() -> new GiftCertificateNotFoundException(id));
    }

    @Override
    public List<GiftCertificate> findAll(int amount, int page) {
        checkPagination(amount, page);
        return giftCertificateDao.findAll(amount, page - 1);
    }

    @Override
    public long delete(long id) {
        if (giftCertificateDao.delete(id)) {
            return id;
        } else {
            throw new GiftCertificateNotFoundException(id);
        }
    }

    @Override
    public List<GiftCertificate> findByParameters(String tagName, String giftValue, String dateSort, String nameSort,
                                                  int amount, int page) {
        checkPagination(amount, page);
        checkSortTypeValid(dateSort);
        checkSortTypeValid(nameSort);
        return giftCertificateDao.findByAttributes(tagName, giftValue, dateSort, nameSort, amount, page - 1);
    }

    private void checkSortTypeValid(String sortType) {
        if (sortType != null && !sortType.equalsIgnoreCase(ASC_SORT) && !sortType.equalsIgnoreCase(DESC_SORT)) {
            throw new ValidationException(messageSource.getMessage("error.gift.sort.type", new Object[]{sortType},
                    LocaleContextHolder.getLocale()));

        }
    }

    @Override
    public List<GiftCertificate> findBySeveralTags(Set<Tag> tags, int amount, int page) {
        checkPagination(amount, page);
        String[] tagNames = new String[tags.size()];
        int i = 0;
        for (Tag tag : tags) {
            String name = tag.getName();
            tagDao.findByName(tag.getName()).orElseThrow(() -> new TagNotFoundException("name=" + name));
            tagNames[i++] = name;
        }
        return giftCertificateDao.findBySeveralTags(tagNames, amount, page - 1);
    }
}
