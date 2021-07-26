package com.epam.esm.service.impl;

import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.converter.ConverterDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.model.dto.converter.ConverterDTO.fromDTO;
import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;


@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String UPDATE_OPTION = "UPDATE";
    private static final String CREATE_OPTION = "CREATE";
    private static final String ASC_SORT = "ASC";
    private static final String DESC_SORT = "DESC";
    private static final String PERCENT_SYMBOL = "%";
    private static final String CREATE_DATE_FIELD = "createDate";
    private static final String NAME_FIELD = "name";

    private final GiftRepository certificateRepository;
    private final TagRepository tagRepository;
    private final MessageSource messageSource;

    @Autowired
    public GiftCertificateServiceImpl(GiftRepository certificateRepository, TagRepository tagRepository, MessageSource messageSource) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GiftCertificateDTO create(GiftCertificateDTO dto) {
        GiftCertificate giftCertificate = fromDTO(dto);
        checkCertificateValid(giftCertificate, CREATE_OPTION);
        checkTagsValid(giftCertificate.getTags());
        Set<Tag> oldTags = detachExistingTags(giftCertificate);
        GiftCertificate createdGift = certificateRepository.save(giftCertificate);
        attachExistingTags(createdGift, oldTags);
        return toDTO(createdGift);
    }

    private Set<Tag> detachExistingTags(GiftCertificate giftCertificate) {
        Set<Tag> tags = giftCertificate.getTags();
        HashSet<Tag> oldTags = new HashSet<>();
        List<Tag> existingTags = tagRepository.findAll();
        for (Tag existingTag : existingTags) {
            for (Tag tag : tags) {
                if (existingTag.getName().equals(tag.getName())) {
                    giftCertificate.removeTag(tag);
                    oldTags.add(tag);
                }
            }
        }
        return oldTags;
    }

    private void attachExistingTags(GiftCertificate giftCertificate, Set<Tag> tagSet) {
        for (Tag oldTag : tagSet) {
            Optional<Tag> optionalTag = tagRepository.findByName(oldTag.getName());
            optionalTag.ifPresent(giftCertificate::addTag);
        }
        certificateRepository.save(giftCertificate);
    }

    private void checkTagsValid(Set<Tag> tagSet) {
        StringBuilder exceptionValidMessage = new StringBuilder();
        for (Tag tag : tagSet) {
            String name = tag.getName();
            if (!TagValidator.isNameValid(name)) {
                exceptionValidMessage.append(messageSource.getMessage("error.tag.validation.name",
                        new Object[]{name}, LocaleContextHolder.getLocale())).append("\n");
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
    @Transactional(propagation = Propagation.REQUIRED)
    public GiftCertificateDTO update(GiftCertificateDTO dto) {
        GiftCertificate giftCertificate = fromDTO(dto);
        long id = giftCertificate.getId();
        GiftCertificate oldCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new GiftCertificateNotFoundException(id));
        checkCertificateValid(giftCertificate, UPDATE_OPTION);
        checkTagsValid(giftCertificate.getTags());
        findUpdatedValues(oldCertificate, giftCertificate);
        oldCertificate.getTags().forEach(giftCertificate::addTag);
        createNewTags(giftCertificate.getTags());
        return toDTO(certificateRepository.save(giftCertificate));
    }

    private void createNewTags(Set<Tag> tagSet) {
        for (Tag tag : tagSet) {
            Optional<Tag> tagOptional = tagRepository.findByName(tag.getName());
            if (tagOptional.isPresent()) {
                Tag existingTag = tagOptional.get();
                tag.setId(existingTag.getId());
                tag.setCreateDate(existingTag.getCreateDate());
                tag.setLastUpdateDate(existingTag.getLastUpdateDate());
            } else {
                tagRepository.save(tag);
            }
        }
    }

    private void findUpdatedValues(GiftCertificate oldCertificate, GiftCertificate newCertificate) {
        newCertificate.setCreateDate(oldCertificate.getCreateDate());
        if (newCertificate.getName() == null) {
            newCertificate.setName(oldCertificate.getName());
        }
        if (newCertificate.getDescription() == null) {
            newCertificate.setDescription(oldCertificate.getDescription());
        }
        if (newCertificate.getPrice() == null) {
            newCertificate.setPrice(oldCertificate.getPrice());
        }
        if (newCertificate.getDuration() == null) {
            newCertificate.setDuration(oldCertificate.getDuration());
        }
    }

    @Override
    public GiftCertificateDTO findById(long id) {
        Optional<GiftCertificate> certificate = certificateRepository.findById(id);
        return toDTO(certificate.orElseThrow(() -> new GiftCertificateNotFoundException(id)));
    }

    @Override
    public List<GiftCertificateDTO> findAll(int amount, int page) {
        checkPagination(amount, page);
        Pageable pageable = PageRequest.of(page - 1, amount);
        List<GiftCertificate> all = certificateRepository.findAll(pageable).toList();
        List<GiftCertificateDTO> dtoList = new ArrayList<>();
        for (GiftCertificate giftCertificate : all) {
            dtoList.add(toDTO(giftCertificate));
        }
        return dtoList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long delete(long id) {
        if (certificateRepository.findById(id).isPresent()) {
            certificateRepository.deleteById(id);
            return id;
        } else {
            throw new GiftCertificateNotFoundException(id);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long removeFromSale(long id) {
        GiftCertificate giftCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new GiftCertificateNotFoundException(id));
        giftCertificate.setForSale(false);
        GiftCertificate updatedCertificate = certificateRepository.save(giftCertificate);
        return updatedCertificate.getId();
    }

    @Override
    public List<GiftCertificateDTO> findByParameters(List<String> tagNames, String giftValue, String dateSort, String nameSort,
                                                     int amount, int page) {
        checkPagination(amount, page);
        checkSortTypeValid(dateSort);
        checkSortTypeValid(nameSort);
        if (giftValue != null) {
            giftValue = PERCENT_SYMBOL + giftValue + PERCENT_SYMBOL;
        }
        Set<String> tags = convertTags(tagNames);
        Pageable pageable = PageRequest.of(page - 1, amount, Sort.by(defineSortDirections(dateSort, nameSort)));
        return certificateRepository.findAllWithParameters(tags, tags.size(), giftValue, pageable).stream()
                .map(ConverterDTO::toDTO).collect(Collectors.toList());
    }

    private List<Sort.Order> defineSortDirections(String dateSort, String nameSort) {
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Direction dateDirection = (dateSort == null || (!dateSort.equalsIgnoreCase(DESC_SORT))) ? Sort.Direction.ASC : Sort.Direction.DESC;
        orders.add(new Sort.Order(dateDirection, CREATE_DATE_FIELD));
        Sort.Direction nameDirection = (nameSort == null || (!nameSort.equalsIgnoreCase(DESC_SORT))) ? Sort.Direction.ASC : Sort.Direction.DESC;
        orders.add(new Sort.Order(nameDirection, NAME_FIELD));
        return orders;
    }

    private void checkSortTypeValid(String sortType) {
        if (sortType != null && !sortType.equalsIgnoreCase(ASC_SORT) && !sortType.equalsIgnoreCase(DESC_SORT)) {
            throw new ValidationException(messageSource.getMessage("error.gift.sort.type", new Object[]{sortType},
                    LocaleContextHolder.getLocale()));
        }
    }

    private Set<String> convertTags(List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return new HashSet<>();
        }
        Set<String> tags = new HashSet<>();
        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name).orElseThrow(() -> new TagNotFoundException("name=" + name));
            tags.add(tag.getName());
        }
        return tags;
    }
}
