package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaTagImpl implements TagDao {

    private final EntityManager entityManager;

    @Autowired
    public JpaTagImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
        Root<Order> orderRoot = query.from(Order.class);
        Join<Object, Object> certificateJoin = orderRoot.join(EntityName.CERTIFICATE).join(EntityName.TAGS);
        query.groupBy(orderRoot.get(EntityName.USER), certificateJoin.get(EntityName.ID));
        query.having(criteriaBuilder.gt(criteriaBuilder.count(certificateJoin.get(EntityName.NAME)), 1));
        List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.asc(criteriaBuilder.count(certificateJoin.get(EntityName.NAME))));
        orderList.add(criteriaBuilder.asc(criteriaBuilder.count(orderRoot.get(EntityName.PRICE))));
        query.orderBy(orderList);
        query.multiselect(certificateJoin.get(EntityName.NAME));
        TypedQuery<Object> typedQuery = entityManager.createQuery(query);
        Optional<Object> name = typedQuery.getResultList().stream().limit(1).findFirst();
        return name.isPresent() ? findByName(((String) name.get())) : Optional.empty();
    }
}
