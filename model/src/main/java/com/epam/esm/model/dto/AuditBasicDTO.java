package com.epam.esm.model.dto;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public abstract class AuditBasicDTO<T extends RepresentationModel<? extends T>> extends BasicDTO<T> {

    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
