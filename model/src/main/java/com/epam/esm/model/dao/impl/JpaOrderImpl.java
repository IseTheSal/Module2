package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaOrderImpl implements OrderDao {

    private final EntityManager entityManager;

    @Autowired
    public JpaOrderImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Order> findById(long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll(int amount, int page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);
        query.select(orderRoot);
        return entityManager.createQuery(query).setMaxResults(amount).setFirstResult(amount * page).getResultList();
    }

    @Override
    public Order create(Order order) {
        entityManager.getTransaction().begin();
        order.setPurchaseDate(LocalDateTime.now());
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        return order;
    }

    @Override
    public List<Order> findUserOrders(long userId, int amount, int page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);
        query.where(criteriaBuilder.equal(orderRoot.get(EntityName.USER), userId));
        return entityManager.createQuery(query).setMaxResults(amount).setFirstResult(amount * page).getResultList();
    }
}
