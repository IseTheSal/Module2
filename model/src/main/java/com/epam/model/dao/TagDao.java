package com.epam.model.dao;

import com.epam.model.entity.Tag;

public interface TagDao extends CommonCRDDao<Tag> {

    boolean isNameExist(String name);
}
