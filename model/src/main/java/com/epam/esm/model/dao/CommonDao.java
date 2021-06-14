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
     * Create new {{@link Entity Entity}} in database.
     *
     * @param entity Any Object that implements {{@link Entity Entity}} interface
     * @return That created entity
     */
    T create(T entity);

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
    List<T> findAll();

    /**
     * Delete {{@link Entity Entity}} from database by provided <code>id</code>.
     *
     * @param id <code>id</code> of object that implements {{@link Entity Entity}}
     * @return <code>True</code> if {{@link Entity Entity}} was deleted, otherwise <code>false</code>
     */
    boolean delete(long id);
}
