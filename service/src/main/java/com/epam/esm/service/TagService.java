package com.epam.esm.service;

import com.epam.esm.model.entity.Entity;
import com.epam.esm.model.entity.Tag;

import java.util.List;

/**
 * Interface extends {@link Tag} service functionality
 *
 * @author Illia Aheyeu
 */

public interface TagService extends CommonEntityService<Tag> {

    /**
     * Intermediate method used to validate {@link Entity Entity} and then call create method from dao layer.
     *
     * @param entity Any Object that implements {@link Entity Entity} interface
     * @return That created entity
     */
    Tag create(Tag entity);

    /**
     * Intermediate method used to validate <code>id</code> of {@link Entity Entity} and then call delete method from dao layer.
     *
     * @param id <code>id</code> of object that implements {{@link Entity Entity}}
     * @return <code>id</code> if object was successfully deleted
     */
    long delete(long id);

    Tag findMostWidelyUsedTag();
}
