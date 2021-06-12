package com.epam.esm.service;


import com.epam.esm.exception.RestErrorStatusCode;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.entity.Entity;
import com.epam.esm.validator.EntityValidator;

import java.util.List;

public interface CommonCRDService<T extends Entity> {

    T create(T entity);

    T findById(String id);

    List<T> findAll();

    long delete(String id);

    default long parseId(String id) {
        if (!EntityValidator.isIdValid(id)) {
            throw new ValidationException("Tag id {" + id + "} is not valid", RestErrorStatusCode.VALIDATION_ERROR);
        }
        return Long.parseLong(id);
    }
}
