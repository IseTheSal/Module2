package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.util.List;

/**
 * Interface extends {@link GiftCertificate} service functionality
 *
 * @author Illia Aheyeu
 */
public interface GiftCertificateService extends CommonEntityService<GiftCertificateDTO> {

    /**
     * Intermediate method used to check on existing, validate {@link GiftCertificateDTO} fields and then call update method from dao layer.
     *
     * @param giftCertificate {@link GiftCertificateDTO}
     * @return Updated version of {@link GiftCertificateDTO}
     */
    GiftCertificateDTO update(GiftCertificateDTO giftCertificate);

    /**
     * Search all {@link GiftCertificateDTO GiftCertificates} with {@link Tag Tag`s} name, {@link GiftCertificate GiftCertificates} name or description.
     * Then sort by date ASC or DESC and by name ASC or DESC.
     * <code><p>ALL VALUES COULD BE OPTIONAL</p></code>
     *
     * @param tags             <code>Set</code> of {@link Tag}
     * @param certificateValue Part of <code>name</code> or <code>description</code> of {@link GiftCertificate}
     * @param dateSort         <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by date of creation
     * @param nameSort         <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by name
     * @return <code>List</code> of {@link GiftCertificateDTO GiftCertificates}
     */
    List<GiftCertificateDTO> findByParameters(List<String> tags, String certificateValue, String dateSort, String nameSort,
                                              int amount, int page);


    /**
     * Intermediate method used to validate {@link GiftCertificateDTO} and then call create method from dao layer.
     *
     * @param entity {@link GiftCertificateDTO}
     * @return That created entity
     */
    GiftCertificateDTO create(GiftCertificateDTO entity);


    /**
     * Remove certificate from sales option
     *
     * @param id <code>id</code> of {@link GiftCertificateDTO}
     * @return <code>id</code> of {@link GiftCertificateDTO}
     */
    long removeFromSale(long id);

    /**
     * Intermediate method used to validate <code>id</code> of {@link GiftCertificateDTO} and then call delete method from dao layer.
     *
     * @param id <code>id</code> of {@link GiftCertificateDTO}
     * @return <code>id</code> if object was successfully deleted
     */
    long delete(long id);
}
