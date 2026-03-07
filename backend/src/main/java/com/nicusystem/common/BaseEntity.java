package com.nicusystem.common;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base entity with common audit fields for all JPA entities.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {

    /** Unique identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Timestamp when the entity was created. */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    /** Timestamp when the entity was last modified. */
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    /** User who created the entity. */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /** User who last modified the entity. */
    @LastModifiedBy
    private String updatedBy;

    /** Optimistic locking version. */
    @Version
    private Long version;
}
