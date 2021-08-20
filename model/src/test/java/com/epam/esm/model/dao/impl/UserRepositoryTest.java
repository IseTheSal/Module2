package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.config.SpringBootTestConfiguration;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ContextConfiguration(classes = {UserRepository.class, SpringBootTestConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@SpringBootTest
@Sql("/data.sql")
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @Test
    void findById() {
        boolean condition = userRepository.findById(1L).isPresent();
        Assertions.assertTrue(condition);
    }

    @Test
    void findAll() {
        List<User> all = userRepository.findAll(PageRequest.of(0, 100)).toList();
        boolean condition = (all.size() >= 2);
        Assertions.assertTrue(condition);
    }
}
