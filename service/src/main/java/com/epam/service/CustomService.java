package com.epam.service;


import com.epam.model.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface CustomService<T extends Entity> {

    T create(T entity);

    Optional<T> findById(long id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(long id);
}
