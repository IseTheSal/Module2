package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.util.List;

/**
 * Interface extends {@link GiftCertificate} database functionality
 *
 * @author Illia Aheyeu
 */
public interface GiftCertificateDao extends CommonDao<GiftCertificate> {
    /**
     * Update only new fields of existing {@link GiftCertificate}.
     *
     * @param giftCertificate {@link GiftCertificate}
     * @return Updated version of {@link GiftCertificate}
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Search all tags of {@link GiftCertificate}.
     *
     * @param id {@link GiftCertificate} <code>id</code> value
     * @return <code>List</code> of {@link Tag} connected to {@link GiftCertificate} with provided <code>id</code>
     */
    List<Tag> findTagsByCertificateId(long id);


    /**
     * Find by {@link GiftCertificate} by attributes.
     * All attributes could be optional.
     *
     * @param tagName       {@link Tag} name
     * @param giftValue     Part of <code>name</code> or <code>description</code> of {@link GiftCertificate}
     * @param dateOrderType Sort order by create_date
     * @param nameOrderType Sort order by {@link GiftCertificate} name
     * @return
     */
    List<GiftCertificate> findByAttributes(String tagName, String giftValue, String dateOrderType,
                                           String nameOrderType);
}
