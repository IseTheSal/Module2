package com.epam.esm.model.entity.audit;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Log4j2
@Component
public class AuditListener {

    @PrePersist
    public void onPrePersist(Object entity) {
        log.info("Insert - " + entity);
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        log.info("Update - " + entity);
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        log.info("Delete - " + entity);
    }
}
