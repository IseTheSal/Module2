package com.epam.esm.service;

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
    List<GiftCertificate> findByParameters(String tagName, String certificateValue, String dateSort, String nameSort,
                                           int amount, int page);


    /**
     * Intermediate method used to validate {@link GiftCertificate} and then call create method from dao layer.
     *
     * @param entity {@link GiftCertificate}
     * @return That created entity
     */
    GiftCertificate create(GiftCertificate entity);


    /**
     * Remove certificate from sales option
     *
     * @param id <code>id</code> of {@link GiftCertificate}
     * @return <code>id</code> of {@link GiftCertificate}
     */
    long removeFromSale(long id);

    /**
     * Intermediate method used to validate <code>id</code> of {@link GiftCertificate} and then call delete method from dao layer.
     *
     * @param id <code>id</code> of {@link GiftCertificate}
     * @return <code>id</code> if object was successfully deleted
     */
    long delete(long id);

    /**
     * Find certificates with 'and' condition
     *
     * @param tags   <code>Set</code> of {@link Tag}
     * @param amount amount
     * @param page   page
     * @return <code>List</code> of {@link GiftCertificate}
     */
    List<GiftCertificate> findBySeveralTags(Set<Tag> tags, int amount, int page);
}
