package com.epam.esm.model.dto;

import org.springframework.hateoas.RepresentationModel;

public abstract class BasicDTO extends RepresentationModel<BasicDTO> {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
