package com.epam.esm.service;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Tag;

/**
 * Interface extends {@link Tag} service functionality
 *
 * @author Illia Aheyeu
 */
public interface TagService extends CommonEntityService<TagDTO> {

    /**
     * Intermediate method used to validate {@link TagDTO} and then call create method from dao layer.
     *
     * @param dto Any Object that implements {@link TagDTO} interface
     * @return That created entity
     */
    TagDTO create(TagDTO dto);

    /**
     * Intermediate method used to validate <code>id</code> of {@link TagDTO} and then call delete method from dao layer.
     *
     * @param id <code>id</code> of object that implements {@link TagDTO}
     * @return <code>id</code> if object was successfully deleted
     */
    long delete(long id);

    /**
     * Find the most widely used tag
     *
     * @return {@link TagDTO}
     */
    TagDTO findMostWidelyUsedTag();
}
