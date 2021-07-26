package com.epam.esm.service.impl;

import com.epam.esm.error.exception.TagExistException;
import com.epam.esm.error.exception.TagNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.converter.ConverterDTO;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.model.dto.converter.ConverterDTO.fromDTO;
import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, UserRepository userRepository, MessageSource messageSource) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
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
        tagRepository.findByName(name).ifPresent(tag1 -> {
            throw new TagExistException(name);
        });
        return toDTO(tagRepository.save(tag));
    }

    @Override
    public TagDTO findById(long id) {
        return toDTO(tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException("id=" + id)));
    }

    @Override
    public List<TagDTO> findAll(int amount, int page) {
        Pageable pageable = PageRequest.of(page - 1, amount);
        return tagRepository.findAll(pageable).stream().map(ConverterDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long delete(long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            tagRepository.delete(tag.get());
            return id;
        } else {
            throw new TagNotFoundException("id=" + id);
        }
    }

    @Override
    public TagDTO findMostWidelyUsedTag() {
        PageRequest page = PageRequest.of(0, 1);
        User user = userRepository.findUserIdWithMostMoneySpent(page).stream().findFirst().orElseThrow(TagNotFoundException::new);
        return toDTO(tagRepository.findMostWidelyUsedTag(user.getId(), page).stream().findFirst()
                .orElseThrow(TagNotFoundException::new));
    }
}
