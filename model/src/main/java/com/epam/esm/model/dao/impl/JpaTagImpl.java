package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaTagImpl implements TagDao {

    private final EntityManagerFactory entityFactory;

    @Autowired
    public JpaTagImpl(EntityManagerFactory entityManagerFactory) {
        this.entityFactory = entityManagerFactory;
    }

    @Override
    public Optional<Tag> findById(long id) {
        EntityManager entityManager = entityFactory.createEntityManager();
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.close();
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> findAll(int amount, int page) {
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = query.from(Tag.class);
        query.select(tagRoot);
        List<Tag> resultList = entityManager.createQuery(query).setMaxResults(amount).setFirstResult(amount * page)
                .getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = query.from(Tag.class);
        query.select(tagRoot).where(criteriaBuilder.equal(tagRoot.get("name"), name));
        Optional<Tag> tag = entityManager.createQuery(query).getResultStream().findAny();
        entityManager.close();
        return tag;
    }

    @Override
    public Tag create(Tag tag) {
        EntityManager entityManager = entityFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(tag);
        entityManager.getTransaction().commit();
        entityManager.close();
        return tag;
    }

    @Override
    public boolean delete(long id) {
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Tag> criteriaDelete = criteriaBuilder.createCriteriaDelete(Tag.class);
        Root<Tag> tagRoot = criteriaDelete.from(Tag.class);
        criteriaDelete.where(criteriaBuilder.equal(tagRoot.get("id"), id));
        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
        boolean isDeleted = (entityManager.createQuery(query).executeUpdate() == 1);
        entityManager.close();
        return isDeleted;
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTag() {
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
        Root<Order> orderRoot = query.from(Order.class);
        Join<Object, Object> certificateJoin = orderRoot.join("certificate").join("tags");
        query.groupBy(orderRoot.get("user"), certificateJoin.get("id"));
        query.having(criteriaBuilder.gt(criteriaBuilder.count(certificateJoin.get("name")), 1));
        List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.asc(criteriaBuilder.count(certificateJoin.get("name"))));
        orderList.add(criteriaBuilder.asc(criteriaBuilder.count(orderRoot.get("price"))));
        query.orderBy(orderList);
        query.multiselect(certificateJoin.get("name"));
        TypedQuery<Object> typedQuery = entityManager.createQuery(query);
        Optional<Object> name = typedQuery.getResultList().stream().limit(1).findFirst();
        entityManager.close();
        return name.isPresent() ? findByName(((String) name.get())) : Optional.empty();
    }
}
