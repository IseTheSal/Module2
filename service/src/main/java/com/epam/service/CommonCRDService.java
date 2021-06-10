package com.epam.service;


import com.epam.model.entity.Entity;

import java.util.List;

public interface CommonCRDService<T extends Entity> {

    T create(T entity);

    T findById(String id);

    List<T> findAll();

    long delete(String id);
}
