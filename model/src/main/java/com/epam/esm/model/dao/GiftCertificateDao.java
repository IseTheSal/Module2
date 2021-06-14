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
     * Search all {@link GiftCertificate GiftCertificates} which matches provided value.
     *
     * @param searchValue Part of <code>name</code> or <code>description</code> of {@link GiftCertificate}
     * @return <code>List</code> of {@link GiftCertificate GiftCertificates} which matches provided <code>searchValue</code>
     */
    List<GiftCertificate> findByNameOrDescription(String searchValue);

    /**
     * Search all {@link GiftCertificate GiftCertificates} with tagName.
     *
     * @param tagName <code>Name</code> of {@link Tag}
     * @return <code>List</code> of {@link GiftCertificate GiftCertificates} which have provided {@link Tag} name
     */
    List<GiftCertificate> findByTag(String tagName);
}
