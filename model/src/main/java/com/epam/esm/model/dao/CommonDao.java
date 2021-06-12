package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface CommonDao<T extends Entity> {
    T create(T entity);

    Optional<T> findById(long id);

    List<T> findAll();

    boolean delete(long id);
}
