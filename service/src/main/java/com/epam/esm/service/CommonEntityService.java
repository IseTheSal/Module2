package com.epam.esm.service;


import com.epam.esm.error.exception.IncorrectPageException;
import com.epam.esm.model.dto.BasicDTO;
import com.epam.esm.validator.PageValidator;

import java.util.List;

/**
 * <p>Service layer interface between Controller and DAO.</p>
 * <p>Generic interface which providing interaction with your service.</p>
 *
 * @param <T> Any Object that extends {@link BasicDTO}  interface
 * @author Illia Aheyeu
 */
public interface CommonEntityService<T extends BasicDTO<T>> {

    /**
     * Intermediate method used to validate <code>id</code> of {@link BasicDTO } and then call find method from dao layer.
     *
     * @param id <code>id</code> of object that implements {@link BasicDTO }
     * @return <code>Optional</code> {@link BasicDTO } from database
     */
    T findById(long id);

    /**
     * Find all specified generic {@link BasicDTO}.
     *
     * @return <code>List</code> of {@link BasicDTO}
     */
    List<T> findAll(int amount, int page);

    /**
     * Check pagination valid
     *
     * @param amount amount of entities in page
     * @param page   page
     */
    default void checkPagination(int amount, int page) {
        if (!PageValidator.isPaginationValid(amount, page)) {
            throw new IncorrectPageException(amount, page);
        }
    }
}
