package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Set;

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
     * Find by {@link GiftCertificate} by attributes.
     * All attributes could be optional.
     *
     * @param tagName       {@link Tag} name
     * @param giftValue     Part of <code>name</code> or <code>description</code> of {@link GiftCertificate}
     * @param dateOrderType Sort order by create_date
     * @param nameOrderType Sort order by {@link GiftCertificate} name
     * @return List of GiftCertificates
     */
    List<GiftCertificate> findByAttributes(String tagName, String giftValue, String dateOrderType,
                                           String nameOrderType, int amount, int page);

    /**
     * Create new {@link GiftCertificate} in database.
     *
     * @param giftCertificate Any Object that implements {@link GiftCertificate} interface
     * @return That created tag
     */
    GiftCertificate create(GiftCertificate giftCertificate);

    /**
     * Delete {@link GiftCertificate} from database by provided <code>id</code>.
     *
     * @param id <code>id</code> of object that implements {@link GiftCertificate}
     * @return <code>True</code> if {@link GiftCertificate} was deleted, otherwise <code>false</code>
     */
    boolean delete(long id);

    /**
     * Search for gift certificates by several tags with 'and' condition.
     *
     * @param tagNames <code>String[]</code> of {@link Tag} names
     * @return <code>List</code> of {@link GiftCertificate}
     */
    List<GiftCertificate> findBySeveralTags(String[] tagNames, int amount, int page);
}
