package com.epam.esm.model.entity.audit;

import lombok.extern.log4j.Log4j2;
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
            AuditEntity<?> auditEntity = (AuditEntity<?>) entity;
            LocalDateTime currentDateTime = LocalDateTime.now();
            auditEntity.setCreateDate(currentDateTime);
            auditEntity.setLastUpdateDate(currentDateTime);
        }
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        if (entity instanceof AuditEntity) {
            AuditEntity<?> auditEntity = (AuditEntity<?>) entity;
            LocalDateTime currentDateTime = LocalDateTime.now();
            auditEntity.setLastUpdateDate(currentDateTime);
        }
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        if (entity instanceof AuditEntity) {
            AuditEntity<?> auditEntity = (AuditEntity<?>) entity;
            auditEntity.setLastUpdateDate(LocalDateTime.now());
        }
    }
}
