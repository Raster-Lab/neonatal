package com.nicusystem.audit;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AuditEvent entity.
 */
class AuditEventTest {

    @Test
    void shouldSetAndGetAction() {
        // Given
        final AuditEvent event = new AuditEvent();

        // When
        event.setAction("CREATE");

        // Then
        assertThat(event.getAction()).isEqualTo("CREATE");
    }

    @Test
    void shouldSetAndGetResourceType() {
        // Given
        final AuditEvent event = new AuditEvent();

        // When
        event.setResourceType("Patient");

        // Then
        assertThat(event.getResourceType()).isEqualTo("Patient");
    }

    @Test
    void shouldSetAndGetResourceId() {
        // Given
        final AuditEvent event = new AuditEvent();
        final String id = UUID.randomUUID().toString();

        // When
        event.setResourceId(id);

        // Then
        assertThat(event.getResourceId()).isEqualTo(id);
    }

    @Test
    void shouldSetAndGetUserId() {
        // Given
        final AuditEvent event = new AuditEvent();

        // When
        event.setUserId("physician");

        // Then
        assertThat(event.getUserId()).isEqualTo("physician");
    }

    @Test
    void shouldSetAndGetDetails() {
        // Given
        final AuditEvent event = new AuditEvent();

        // When
        event.setDetails("Patient record created");

        // Then
        assertThat(event.getDetails()).isEqualTo("Patient record created");
    }

    @Test
    void shouldSetAndGetIpAddress() {
        // Given
        final AuditEvent event = new AuditEvent();

        // When
        event.setIpAddress("192.168.1.1");

        // Then
        assertThat(event.getIpAddress()).isEqualTo("192.168.1.1");
    }

    @Test
    void shouldSetAndGetTimestamp() {
        // Given
        final AuditEvent event = new AuditEvent();
        final Instant now = Instant.now();

        // When
        event.setTimestamp(now);

        // Then
        assertThat(event.getTimestamp()).isEqualTo(now);
    }
}
