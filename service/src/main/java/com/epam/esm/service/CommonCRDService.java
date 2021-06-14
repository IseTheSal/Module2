package com.epam.esm.service;


import com.epam.esm.exception.RestErrorStatusCode;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.entity.Entity;
import com.epam.esm.validator.EntityValidator;

import java.util.List;

/**
 * <p>Layer service interface between Controller and DAO.</p>
 * <p>Generic interface which providing interaction with your service.</p>
 *
 * @param <T> Any Object that implements {@link Entity Entity} interface
 * @author Illia Aheyeu
 */
public interface CommonCRDService<T extends Entity> {

    /**
     * Intermediate method used to validate {@link Entity Entity} and then call create method from dao layer.
     *
     * @param entity Any Object that implements {@link Entity Entity} interface
     * @return That created entity
     */
    T create(T entity);

    /**
     * Intermediate method used to validate <code>id</code> of {@link Entity Entity} and then call find method from dao layer.
     *
     * @param id <code>id</code> of object that implements {{@link Entity Entity}}
     * @return <code>Optional</code> {{@link Entity Entity}}  from database
     */
    T findById(String id);

    /**
     * Find all specified generic {{@link Entity Entity}}.
     *
     * @return <code>List</code> of {{@link Entity Entity}}
     */
    List<T> findAll();

    /**
     * Intermediate method used to validate <code>id</code> of {@link Entity Entity} and then call delete method from dao layer.
     *
     * @param id <code>id</code> of object that implements {{@link Entity Entity}}
     * @return <code>id</code> if object was successfully deleted
     */
    long delete(String id);

    /**
     * Parse provided String id value, if invalid id was provided throw {@link ValidationException}
     *
     * @param id <code>String</code> id value of {@link Entity}
     * @return <code>long</code> value
     * @see ValidationException
     */
    default long parseId(String id) {
        if (!EntityValidator.isIdValid(id)) {
            //fixme message source
            throw new ValidationException(id, RestErrorStatusCode.VALIDATION_ERROR);
        }
        return Long.parseLong(id);
    }
}
