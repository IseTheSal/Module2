package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;

import java.util.Optional;

public interface TagDao extends CommonDao<Tag> {

    Optional<Tag> findByName(String name);
}
