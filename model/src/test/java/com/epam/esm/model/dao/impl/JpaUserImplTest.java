//package com.epam.esm.model.dao.impl;
//
//import com.epam.esm.model.dao.UserDao;
//import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
//import com.epam.esm.model.entity.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//
//import java.util.List;
//
//@ContextConfiguration(classes = {JpaUserImpl.class, SpringBootTestConfiguration.class},
//        loader = AnnotationConfigContextLoader.class)
//@DirtiesContext
//@SpringBootTest
//public class JpaUserImplTest {
//
//    @Autowired
//    UserDao userDao;
//
//    @Test
//    void findById() {
//        boolean condition = userDao.findById(1).isPresent();
//        Assertions.assertTrue(condition);
//    }
//
//    @Test
//    void findAll() {
//        List<User> all = userDao.findAll(100, 0);
//        boolean condition = (all.size() >= 2);
//        Assertions.assertTrue(condition);
//    }
//}
