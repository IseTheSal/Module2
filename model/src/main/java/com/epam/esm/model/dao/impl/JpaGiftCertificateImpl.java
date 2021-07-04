package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaGiftCertificateImpl implements GiftCertificateDao {

    private static final String DESC = "DESC";
    private static final String PERCENT = "%";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findAll(int amount, int page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        query.select(certificateRoot);
        return entityManager.createQuery(query).setMaxResults(amount).setFirstResult(page * amount).getResultList();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        if (giftCertificate.getLastUpdateDate() == null) {
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
        }
        entityManager.merge(giftCertificate);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findByAttributes(String[] tagNames, String giftValue, String dateOrderType,
                                                  String nameOrderType, int amount, int page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        query.select(certificateRoot);
        List<Predicate> wherePredicates = new ArrayList<>();
        if (tagNames.length != 0) {
            Join<Object, Object> tags = certificateRoot.join(EntityField.TAGS);
            wherePredicates.add(tags.get(EntityField.NAME).in(tagNames));
            query.groupBy(certificateRoot.get(EntityField.ID))
                    .having(criteriaBuilder.equal(criteriaBuilder.countDistinct(tags.get(EntityField.NAME)), tagNames.length));
        }
        if (giftValue != null) {
            Predicate name = criteriaBuilder.like(certificateRoot
                    .get(EntityField.NAME), PERCENT + giftValue + PERCENT);
            Predicate description = criteriaBuilder.like(certificateRoot
                    .get(EntityField.DESCRIPTION), PERCENT + giftValue + PERCENT);
            wherePredicates.add(criteriaBuilder.or(name, description));
        }
        if (dateOrderType != null && nameOrderType != null) {
            Order createDate = dateOrderType.equalsIgnoreCase(DESC.trim())
                    ? criteriaBuilder.desc(certificateRoot.get(EntityField.CREATE_DATE))
                    : criteriaBuilder.asc(certificateRoot.get(EntityField.CREATE_DATE));
            Order name = nameOrderType.equalsIgnoreCase(DESC.trim())
                    ? criteriaBuilder.desc(certificateRoot.get(EntityField.NAME))
                    : criteriaBuilder.asc(certificateRoot.get(EntityField.NAME));
            query.orderBy(createDate, name);
        } else if (dateOrderType != null) {
            Order createDate = dateOrderType.equalsIgnoreCase(DESC.trim())
                    ? criteriaBuilder.desc(certificateRoot.get(EntityField.CREATE_DATE))
                    : criteriaBuilder.asc(certificateRoot.get(EntityField.CREATE_DATE));
            query.orderBy(createDate);
        } else if (nameOrderType != null) {
            Order name = nameOrderType.equalsIgnoreCase(DESC.trim())
                    ? criteriaBuilder.desc(certificateRoot.get(EntityField.NAME))
                    : criteriaBuilder.asc(certificateRoot.get(EntityField.NAME));
            query.orderBy(name);
        }
        query.where(wherePredicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(query).setMaxResults(amount).setFirstResult(page * amount).getResultList();
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        LocalDateTime now = LocalDateTime.now();
        giftCertificate.setCreateDate(now);
        giftCertificate.setLastUpdateDate(now);
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public boolean delete(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<GiftCertificate> criteriaDelete = criteriaBuilder.createCriteriaDelete(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaDelete.from(GiftCertificate.class);
        criteriaDelete.where(criteriaBuilder.equal(certificateRoot.get(EntityField.ID), id));
        return (entityManager.createQuery(criteriaDelete).executeUpdate() > 0);
    }
}
