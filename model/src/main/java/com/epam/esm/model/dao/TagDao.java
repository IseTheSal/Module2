package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;

import java.util.Optional;

/**
 * Interface extends {@link Tag} database functionality
 *
 * @author Illia Aheyeu
 */
public interface TagDao extends CommonDao<Tag> {

    /**
     * Search {@link Tag} by name
     *
     * @param name {@link Tag}  name
     * @return <code>Optional</code> {@link Tag}
     */
    Optional<Tag> findByName(String name);
}
