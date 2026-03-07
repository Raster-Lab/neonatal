package com.nicusystem.audit;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing an audit event in the system.
 */
@Entity
@Table(name = "audit_event")
@Getter
@Setter
@NoArgsConstructor
public class AuditEvent extends BaseEntity {

    /** The action that was performed. */
    @Column(nullable = false)
    private String action;

    /** The type of resource affected. */
    @Column(name = "resource_type")
    private String resourceType;

    /** The identifier of the resource affected. */
    @Column(name = "resource_id")
    private String resourceId;

    /** The identifier of the user who performed the action. */
    @Column(name = "user_id")
    private String userId;

    /** Additional details about the event. */
    @Column(columnDefinition = "TEXT")
    private String details;

    /** The IP address from which the action originated. */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /** The timestamp when the event occurred. */
    @Column(nullable = false)
    private Instant timestamp;
}
