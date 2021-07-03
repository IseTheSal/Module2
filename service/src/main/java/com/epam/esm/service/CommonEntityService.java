package com.epam.esm.service;


import com.epam.esm.error.exception.IncorrectPageException;
import com.epam.esm.model.entity.Entity;
import com.epam.esm.validator.PageValidator;

import java.util.List;

/**
 * <p>Layer service interface between Controller and DAO.</p>
 * <p>Generic interface which providing interaction with your service.</p>
 *
 * @param <T> Any Object that implements {@link Entity}  interface
 * @author Illia Aheyeu
 */
public interface CommonEntityService<T extends Entity> {

    /**
     * Intermediate method used to validate <code>id</code> of {@link Entity Entity} and then call find method from dao layer.
     *
     * @param id <code>id</code> of object that implements {{@link Entity Entity}}
     * @return <code>Optional</code> {{@link Entity Entity}}  from database
     */
    T findById(long id);

    /**
     * Find all specified generic {{@link Entity Entity}}.
     *
     * @return <code>List</code> of {{@link Entity Entity}}
     */
    List<T> findAll(int amount, int page);

    default void checkPagination(int amount, int page) {
        if (!PageValidator.isPaginationValid(amount, page)) {
            throw new IncorrectPageException(amount, page);
        }
    }
}
