package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaGiftCertificateImpl implements GiftCertificateDao {

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<GiftCertificate> findAll(int amount, int page) {
        return null;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public List<Tag> findTagsByCertificateId(long id) {
        return null;
    }

    @Override
    public List<GiftCertificate> findByAttributes(String tagName, String giftValue, String dateOrderType, String nameOrderType, int amount, int page) {
        return null;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<GiftCertificate> findBySeveralTags(String[] tagNames, int amount, int page) {
        return null;
    }
}
