package com.nicusystem.audit;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AuditEvent entities.
 */
@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {

    /**
     * Finds all audit events for a given user.
     *
     * @param userId the user identifier
     * @return list of audit events
     */
    List<AuditEvent> findByUserId(String userId);

    /**
     * Finds all audit events for a given resource type.
     *
     * @param resourceType the resource type
     * @return list of audit events
     */
    List<AuditEvent> findByResourceType(String resourceType);
}
