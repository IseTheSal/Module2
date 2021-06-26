package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaGiftCertificateImpl implements GiftCertificateDao {

    private final EntityManagerFactory entityFactory;

    @Autowired
    public JpaGiftCertificateImpl(EntityManagerFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        EntityManager entityManager = entityFactory.createEntityManager();
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.close();
        return Optional.ofNullable(giftCertificate);
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
