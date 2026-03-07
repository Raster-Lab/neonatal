package com.nicusystem.audit;

import java.time.Instant;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing audit events.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditEventRepository auditEventRepository;

    /**
     * Logs an audit event.
     *
     * @param action       the action performed
     * @param resourceType the type of resource
     * @param resourceId   the resource identifier
     * @param userId       the user identifier
     * @param details      additional details
     * @param ipAddress    the originating IP address
     * @return the persisted audit event
     */
    @Transactional
    public AuditEvent logEvent(final String action, final String resourceType,
            final String resourceId, final String userId,
            final String details, final String ipAddress) {
        log.info("Audit event: action={}, resourceType={}, resourceId={}, userId={}",
                action, resourceType, resourceId, userId);

        final AuditEvent event = new AuditEvent();
        event.setAction(action);
        event.setResourceType(resourceType);
        event.setResourceId(resourceId);
        event.setUserId(userId);
        event.setDetails(details);
        event.setIpAddress(ipAddress);
        event.setTimestamp(Instant.now());

        return auditEventRepository.save(event);
    }

    /**
     * Finds audit events by user identifier.
     *
     * @param userId the user identifier
     * @return list of audit events
     */
    @Transactional(readOnly = true)
    public List<AuditEvent> findByUserId(final String userId) {
        return auditEventRepository.findByUserId(userId);
    }

    /**
     * Finds audit events by resource type.
     *
     * @param resourceType the resource type
     * @return list of audit events
     */
    @Transactional(readOnly = true)
    public List<AuditEvent> findByResourceType(final String resourceType) {
        return auditEventRepository.findByResourceType(resourceType);
    }
}
