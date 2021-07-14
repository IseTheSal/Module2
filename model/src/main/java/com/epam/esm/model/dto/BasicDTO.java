package com.epam.esm.model.dto;

import org.springframework.hateoas.RepresentationModel;

public abstract class BasicDTO<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
