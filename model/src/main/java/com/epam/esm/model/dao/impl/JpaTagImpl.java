package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaTagImpl implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> findAll(int amount, int page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = query.from(Tag.class);
        query.select(tagRoot);
        return entityManager.createQuery(query).setMaxResults(amount).setFirstResult(amount * page).getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = query.from(Tag.class);
        query.select(tagRoot).where(criteriaBuilder.equal(tagRoot.get(EntityName.NAME), name));
        return entityManager.createQuery(query).getResultStream().findAny();
    }

    @Override
    public boolean delete(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Tag> criteriaDelete = criteriaBuilder.createCriteriaDelete(Tag.class);
        Root<Tag> tagRoot = criteriaDelete.from(Tag.class);
        criteriaDelete.where(criteriaBuilder.equal(tagRoot.get(EntityName.ID), id));
        return (entityManager.createQuery(criteriaDelete).executeUpdate() == 1);
    }


    @Override
    public Optional<Tag> findMostWidelyUsedTag() {
        long userId = findUserIdWithMostMoneySpent();
        String tagName = findPopularTagOfUser(userId);
        return findByName(tagName);
    }

    private long findUserIdWithMostMoneySpent() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
        Root<Order> orderRoot = query.from(Order.class);
        Path<Object> orderUser = orderRoot.get(EntityName.USER).get(EntityName.ID);
        query.select(orderUser).groupBy(orderUser).orderBy(criteriaBuilder
                .desc(criteriaBuilder.sum(orderRoot.get(EntityName.PRICE))));
        Optional<Object> userOptional = entityManager.createQuery(query).getResultList().stream().findFirst();
        return ((Long) userOptional.get());
    }

    private String findPopularTagOfUser(long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
        Root<Order> orderRoot = query.from(Order.class);
        query.where(criteriaBuilder.equal(orderRoot.get(EntityName.USER).get(EntityName.ID), userId));
        Path<Object> joinOrderTag = orderRoot.join(EntityName.CERTIFICATE).join(EntityName.TAGS).get(EntityName.NAME);
        query.select(joinOrderTag).groupBy(joinOrderTag).orderBy(criteriaBuilder.desc(criteriaBuilder.count(joinOrderTag)));
        Optional<Object> tagName = entityManager.createQuery(query).setMaxResults(1).getResultList().stream().findFirst();
        return (String) tagName.get();
    }
}
