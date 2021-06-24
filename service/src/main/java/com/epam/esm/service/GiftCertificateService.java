package com.epam.esm.service;

import com.epam.esm.model.entity.Entity;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * Interface extends {@link GiftCertificate} service functionality
 *
 * @author Illia Aheyeu
 */
public interface GiftCertificateService extends CommonEntityService<GiftCertificate> {

    /**
     * Intermediate method used to check on existing, validate {@link GiftCertificate} fields and then call update method from dao layer.
     *
     * @param giftCertificate {@link GiftCertificate}
     * @return Updated version of {@link GiftCertificate}
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Search all {@link GiftCertificate GiftCertificates} with {@link Tag Tag`s} name, {@link GiftCertificate GiftCertificates} name or description.
     * Then sort by date ASC or DESC and by name ASC or DESC.
     * <code><p>ALL VALUES COULD BE OPTIONAL</p></code>
     *
     * @param tagName          <code>Name</code> of {@link Tag}
     * @param certificateValue Part of <code>name</code> or <code>description</code> of {@link GiftCertificate}
     * @param dateSort         <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by date of creation
     * @param nameSort         <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by name
     * @return <code>List</code> of {@link GiftCertificate GiftCertificates}
     */
    List<GiftCertificate> findByParameters(String tagName, String certificateValue,
                                           String dateSort, String nameSort);


    /**
     * Intermediate method used to validate {@link Entity Entity} and then call create method from dao layer.
     *
     * @param entity Any Object that implements {@link Entity Entity} interface
     * @return That created entity
     */
    GiftCertificate create(GiftCertificate entity);


    /**
     * Intermediate method used to validate <code>id</code> of {@link Entity Entity} and then call delete method from dao layer.
     *
     * @param id <code>id</code> of object that implements {{@link Entity Entity}}
     * @return <code>id</code> if object was successfully deleted
     */
    long delete(long id);

    List<GiftCertificate> findBySeveralTags(Set<Tag> tags);
}
