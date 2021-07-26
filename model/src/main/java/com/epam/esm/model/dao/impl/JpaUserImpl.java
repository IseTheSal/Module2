//package com.epam.esm.model.dao.impl;
//
//import com.epam.esm.model.dao.UserDao;
//import com.epam.esm.model.entity.User;
//import com.epam.esm.model.entity.UserRole;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class JpaUserImpl implements UserDao {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public Optional<User> findById(long id) {
//        User user = entityManager.find(User.class, id);
//        return Optional.ofNullable(user);
//    }
//
//    @Override
//    public List<User> findAll(int amount, int page) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
//        Root<User> userRoot = query.from(User.class);
//        query.select(userRoot);
//        return entityManager.createQuery(query).setMaxResults(amount).setFirstResult(amount * page).getResultList();
//    }
//
//    @Override
//    public Optional<User> findByLogin(String login) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
//        Root<User> userRoot = query.from(User.class);
//        query.select(userRoot).where(criteriaBuilder.equal(userRoot.get(EntityField.LOGIN), login));
//        return entityManager.createQuery(query).getResultStream().findFirst();
//    }
//
//    @Override
//    public Optional<UserRole> findRoleByName(String name) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<UserRole> query = criteriaBuilder.createQuery(UserRole.class);
//        Root<UserRole> roleRoot = query.from(UserRole.class);
//        query.select(roleRoot).where(criteriaBuilder.equal(roleRoot.get(EntityField.NAME), name));
//        return entityManager.createQuery(query).getResultStream().findFirst();
//    }
//
//    @Override
//    public User create(User user) {
//        entityManager.persist(user);
//        return user;
//    }
//}
