package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 * <p>Generic interface which providing interaction with your database.</p>
 *
 * @param <T> Any Object that implements {{@link Entity Entity}} interface
 * @author Illia Aheyeu
 */
public interface CommonDao<T extends Entity> {
    /**
     * Search {{@link Entity Entity}} by provided <code>id</code> in database.
     *
     * @param id <code>id</code> of object that implements {{@link Entity Entity}}
     * @return <code>Optional</code> {{@link Entity Entity}}  from database
     */
    Optional<T> findById(long id);

    /**
     * Find all specified generic {{@link Entity Entity}}  from database
     *
     * @return <code>List</code> of {{@link Entity Entity}}
     */
    List<T> findAll(int amount, int page);
}
