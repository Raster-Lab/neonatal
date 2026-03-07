package com.nicusystem.config;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.AuditorAware;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AuditConfig.
 */
class AuditConfigTest {

    @Test
    void auditorProvider_shouldReturnAuditorAwareInstance() {
        // Given
        final AuditConfig config = new AuditConfig();

        // When
        final AuditorAware<String> auditorAware = config.auditorProvider();

        // Then
        assertThat(auditorAware).isNotNull();
    }
}
