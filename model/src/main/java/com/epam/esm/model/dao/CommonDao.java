package com.epam.esm.model.dao;

import com.epam.esm.model.entity.EntityMarker;

import java.util.List;
import java.util.Optional;

/**
 * <p>Generic interface which providing interaction with your database.</p>
 *
 * @param <T> Any Object that implements {@link EntityMarker Entity} interface
 * @author Illia Aheyeu
 */
public interface CommonDao<T extends EntityMarker> {
    /**
     * Search {@link EntityMarker Entity} by provided <code>id</code> in database.
     *
     * @param id <code>id</code> of object that implements {@link EntityMarker Entity}
     * @return <code>Optional</code> {@link EntityMarker Entity}  from database
     */
    Optional<T> findById(long id);

    /**
     * Find all specified generic {@link EntityMarker Entity}  from database
     *
     * @return <code>List</code> of {@link EntityMarker Entity}
     */
    List<T> findAll(int amount, int page);
}
