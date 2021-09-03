package com.epam.esm.model.entity.audit;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Log4j2
@Component
public class AuditListener {

    @PrePersist
    public void onPrePersist(Object entity) {
        if (entity instanceof AuditEntity) {
            AuditEntity auditEntity = (AuditEntity) entity;
            LocalDateTime currentDateTime = LocalDateTime.now();
            auditEntity.setCreateDate(currentDateTime);
            auditEntity.setLastUpdateDate(currentDateTime);
        }
        log.log(Level.INFO, "Insert - " + entity);
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        if (entity instanceof AuditEntity) {
            AuditEntity auditEntity = (AuditEntity) entity;
            auditEntity.setLastUpdateDate(LocalDateTime.now());
        }
        log.log(Level.INFO, "Update - " + entity);
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        if (entity instanceof AuditEntity) {
            AuditEntity auditEntity = (AuditEntity) entity;
            auditEntity.setLastUpdateDate(LocalDateTime.now());
        }
        log.log(Level.INFO, "Remove - " + entity);
    }
}
