package com.nicusystem.security;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AuditAwareImpl.
 */
class AuditAwareImplTest {

    private final AuditAwareImpl auditAware = new AuditAwareImpl();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentAuditor_noAuthentication_shouldReturnSystem() {
        // Given
        SecurityContextHolder.clearContext();

        // When
        final Optional<String> auditor = auditAware.getCurrentAuditor();

        // Then
        assertThat(auditor).isPresent().contains("system");
    }

    @Test
    void getCurrentAuditor_authenticatedUser_shouldReturnUsername() {
        // Given
        final var auth = new UsernamePasswordAuthenticationToken(
                "physician", "password", java.util.Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // When
        final Optional<String> auditor = auditAware.getCurrentAuditor();

        // Then
        assertThat(auditor).isPresent().contains("physician");
    }

    @Test
    void getCurrentAuditor_anonymousUser_shouldReturnSystem() {
        // Given - use 3-arg constructor so isAuthenticated=true, testing anonymousUser branch
        final var auth = new UsernamePasswordAuthenticationToken(
                "anonymousUser", null, java.util.Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // When
        final Optional<String> auditor = auditAware.getCurrentAuditor();

        // Then
        assertThat(auditor).isPresent().contains("system");
    }

    @Test
    void getCurrentAuditor_notAuthenticated_shouldReturnSystem() {
        // Given
        final var auth = new UsernamePasswordAuthenticationToken("user", "pass");
        SecurityContextHolder.getContext().setAuthentication(auth);

        // When
        final Optional<String> auditor = auditAware.getCurrentAuditor();

        // Then
        assertThat(auditor).isPresent().contains("system");
    }
}
