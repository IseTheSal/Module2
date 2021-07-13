package com.epam.esm.service;


import com.epam.esm.error.exception.IncorrectPageException;
import com.epam.esm.model.entity.EntityMarker;
import com.epam.esm.validator.PageValidator;

import java.util.List;

/**
 * <p>Layer service interface between Controller and DAO.</p>
 * <p>Generic interface which providing interaction with your service.</p>
 *
 * @param <T> Any Object that implements {@link EntityMarker}  interface
 * @author Illia Aheyeu
 */
public interface CommonEntityService<T extends EntityMarker> {

    /**
     * Intermediate method used to validate <code>id</code> of {@link EntityMarker Entity} and then call find method from dao layer.
     *
     * @param id <code>id</code> of object that implements {@link EntityMarker Entity}
     * @return <code>Optional</code> {@link EntityMarker Entity} from database
     */
    T findById(long id);

    /**
     * Find all specified generic {@link EntityMarker Entity}.
     *
     * @return <code>List</code> of {@link EntityMarker Entity}
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
