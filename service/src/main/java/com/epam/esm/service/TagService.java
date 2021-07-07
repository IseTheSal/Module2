package com.epam.esm.service;

import com.epam.esm.model.entity.EntityMarker;
import com.epam.esm.model.entity.Tag;

/**
 * Interface extends {@link Tag} service functionality
 *
 * @author Illia Aheyeu
 */
public interface TagService extends CommonEntityService<Tag> {

    /**
     * Intermediate method used to validate {@link EntityMarker Entity} and then call create method from dao layer.
     *
     * @param entity Any Object that implements {@link EntityMarker Entity} interface
     * @return That created entity
     */
    Tag create(Tag entity);

    /**
     * Intermediate method used to validate <code>id</code> of {@link EntityMarker Entity} and then call delete method from dao layer.
     *
     * @param id <code>id</code> of object that implements {{@link EntityMarker Entity}}
     * @return <code>id</code> if object was successfully deleted
     */
    long delete(long id);

    /**
     * Find the most widely used tag
     *
     * @return {@link Tag}
     */
    Tag findMostWidelyUsedTag();
}
