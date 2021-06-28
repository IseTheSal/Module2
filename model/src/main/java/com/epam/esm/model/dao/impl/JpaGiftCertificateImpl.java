package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaGiftCertificateImpl implements GiftCertificateDao {

    private static final String DESC = "DESC";
    private static final String PERCENT = "%";

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
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        query.select(certificateRoot);
        List<GiftCertificate> resultList = entityManager.createQuery(query).setMaxResults(amount)
                .setFirstResult(page * amount).getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        EntityManager entityManager = entityFactory.createEntityManager();
        if (giftCertificate.getLastUpdateDate() == null) {
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
        }
        entityManager.getTransaction().begin();
        entityManager.merge(giftCertificate);
        entityManager.getTransaction().commit();
        entityManager.close();
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findByAttributes(String tagName, String giftValue, String dateOrderType,
                                                  String nameOrderType, int amount, int page) {
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        query.select(certificateRoot);
        if (tagName != null) {
            Join<Object, Object> tags = certificateRoot.join(EntityName.TAGS);
            query.where(criteriaBuilder.equal(tags.get(EntityName.NAME), tagName));
        }
        if (giftValue != null) {
            Predicate name = criteriaBuilder.like(certificateRoot
                    .get(EntityName.NAME), PERCENT + giftValue + PERCENT);
            Predicate description = criteriaBuilder.like(certificateRoot
                    .get(EntityName.DESCRIPTION), PERCENT + giftValue + PERCENT);
            query.where(criteriaBuilder.or(name, description));
        }
        if (dateOrderType != null && nameOrderType != null) {
            Order createDate = dateOrderType.equalsIgnoreCase(DESC.trim())
                    ? criteriaBuilder.desc(certificateRoot.get(EntityName.CREATE_DATE))
                    : criteriaBuilder.asc(certificateRoot.get(EntityName.CREATE_DATE));
            Order name = nameOrderType.equalsIgnoreCase(DESC.trim())
                    ? criteriaBuilder.desc(certificateRoot.get(EntityName.NAME))
                    : criteriaBuilder.asc(certificateRoot.get(EntityName.NAME));
            query.orderBy(createDate, name);
        } else if (dateOrderType != null) {
            Order createDate = dateOrderType.equalsIgnoreCase(DESC.trim())
                    ? criteriaBuilder.desc(certificateRoot.get(EntityName.CREATE_DATE))
                    : criteriaBuilder.asc(certificateRoot.get(EntityName.CREATE_DATE));
            query.orderBy(createDate);
        } else if (nameOrderType != null) {
            Order name = nameOrderType.equalsIgnoreCase(DESC.trim())
                    ? criteriaBuilder.desc(certificateRoot.get(EntityName.NAME))
                    : criteriaBuilder.asc(certificateRoot.get(EntityName.NAME));
            query.orderBy(name);
        }
        List<GiftCertificate> resultList = entityManager.createQuery(query).setMaxResults(amount)
                .setFirstResult(page * amount).getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        EntityManager entityManager = entityFactory.createEntityManager();
        LocalDateTime now = LocalDateTime.now();
        giftCertificate.setCreateDate(now);
        giftCertificate.setLastUpdateDate(now);
        entityManager.getTransaction().begin();
        entityManager.persist(giftCertificate);
        entityManager.getTransaction().commit();
        entityManager.close();
        return giftCertificate;
    }

    @Override
    public boolean delete(long id) {
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<GiftCertificate> criteriaDelete = criteriaBuilder.createCriteriaDelete(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaDelete.from(GiftCertificate.class);
        criteriaDelete.where(criteriaBuilder.equal(certificateRoot.get(EntityName.ID), id));
        entityManager.getTransaction().begin();
        boolean isDeleted = (entityManager.createQuery(criteriaDelete).executeUpdate() > 0);
        entityManager.getTransaction().commit();
        entityManager.close();
        return isDeleted;
    }

    @Override
    public List<GiftCertificate> findBySeveralTags(String[] tagNames, int amount, int page) {
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        Join<Object, Object> tags = certificateRoot.join(EntityName.TAGS);
        query.select(certificateRoot).where(tags.get(EntityName.NAME).in(tagNames));
        List<GiftCertificate> resultList = entityManager.createQuery(query).setMaxResults(amount)
                .setFirstResult(page * amount).getResultList();
        entityManager.close();
        return resultList;
    }
}
