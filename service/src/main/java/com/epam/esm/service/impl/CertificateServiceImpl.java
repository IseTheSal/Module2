package com.epam.esm.service.impl;

import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.RestErrorStatusCode;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class CertificateServiceImpl implements CertificateService {

    private static final String UPDATE_OPTION = "UPDATE";
    private static final String CREATE_OPTION = "CREATE";

    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public CertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        checkCertificateValid(giftCertificate, CREATE_OPTION);
        checkTagsValid(giftCertificate.getTags());
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        giftCertificate.setCreateDate(currentDateTime);
        giftCertificate.setLastUpdateDate(currentDateTime);
        return giftCertificateDao.create(giftCertificate);
    }

    private void checkTagsValid(Set<Tag> tagSet) {
        StringBuilder exceptionValidMessage = new StringBuilder();
        for (Tag tag : tagSet) {
            if (!TagValidator.isNameValid(tag.getName())) {
                exceptionValidMessage.append(tag.getName()).append(" is invalid\n");
            }
        }
        String message = exceptionValidMessage.toString();
        if (!message.isEmpty()) {
            throw new ValidationException(message, RestErrorStatusCode.VALIDATION_ERROR);
        }
    }

    private void checkCertificateValid(GiftCertificate giftCertificate, String option) {
        StringBuilder exceptionValidMessage = new StringBuilder();
        String name = giftCertificate.getName();
        if ((name != null && !GiftCertificateValidator.isNameValid(name) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isNameValid(name) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append("Name ").append(name).append(" is invalid. ");
        }
        String description = giftCertificate.getDescription();
        if ((description != null
                && !GiftCertificateValidator.isDescriptionValid(description) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isDescriptionValid(description) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append("Description ").append(description).append(" is invalid. ");
        }
        BigDecimal price = giftCertificate.getPrice();
        if ((price != null && !GiftCertificateValidator.isPriceValid(price) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isPriceValid(price) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append("Price ").append(price).append(" is invalid. ");
        }
        Integer duration = giftCertificate.getDuration();
        if ((duration != null && !GiftCertificateValidator.isDurationValid(duration) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isDurationValid(duration) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append("Duration ").append(duration).append(" is invalid");
        }
        String message = exceptionValidMessage.toString();
        if (!message.isEmpty()) {
            throw new ValidationException(message, RestErrorStatusCode.VALIDATION_ERROR);
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        long id = giftCertificate.getId();
        if (!giftCertificateDao.findById(id).isPresent()) {
            throw new GiftCertificateNotFoundException("Certificate with id " + id + " not found",
                    RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
        checkCertificateValid(giftCertificate, UPDATE_OPTION);
        checkTagsValid(giftCertificate.getTags());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now());
        return giftCertificateDao.update(giftCertificate);
    }

    @Override
    public GiftCertificate findById(String id) {
        long certificateId = parseId(id);
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(certificateId);
        if (optionalGiftCertificate.isPresent()) {
            return optionalGiftCertificate.get();
        } else {
            throw new GiftCertificateNotFoundException("Certificate with id (" + id + ") does not exist", RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public long delete(String id) {
        long idValue = parseId(id);
        if (giftCertificateDao.delete(idValue)) {
            return idValue;
        } else {
            throw new GiftCertificateNotFoundException("Certificate with id (" + id + ") does not exist", RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
    }
}
