package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.util.List;

public interface GiftCertificateDao extends CommonDao<GiftCertificate> {
    GiftCertificate update(GiftCertificate giftCertificate);

    List<Tag> findTagsByCertificateId(long id);
}
