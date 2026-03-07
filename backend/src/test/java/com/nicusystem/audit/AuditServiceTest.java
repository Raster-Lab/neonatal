package com.nicusystem.audit;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for AuditService.
 */
@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private AuditEventRepository auditEventRepository;

    @InjectMocks
    private AuditService auditService;

    @Test
    void logEvent_shouldSaveAuditEvent() {
        // Given
        final AuditEvent savedEvent = new AuditEvent();
        savedEvent.setAction("CREATE");
        when(auditEventRepository.save(any(AuditEvent.class))).thenReturn(savedEvent);

        // When
        final AuditEvent result = auditService.logEvent(
                "CREATE", "Patient", "123", "physician", "Created patient", "127.0.0.1"
        );

        // Then
        final ArgumentCaptor<AuditEvent> captor = ArgumentCaptor.forClass(AuditEvent.class);
        verify(auditEventRepository).save(captor.capture());
        final AuditEvent captured = captor.getValue();
        assertThat(captured.getAction()).isEqualTo("CREATE");
        assertThat(captured.getResourceType()).isEqualTo("Patient");
        assertThat(captured.getResourceId()).isEqualTo("123");
        assertThat(captured.getUserId()).isEqualTo("physician");
        assertThat(captured.getDetails()).isEqualTo("Created patient");
        assertThat(captured.getIpAddress()).isEqualTo("127.0.0.1");
        assertThat(captured.getTimestamp()).isNotNull();
        assertThat(result).isEqualTo(savedEvent);
    }

    @Test
    void findByUserId_shouldReturnEvents() {
        // Given
        final AuditEvent event = new AuditEvent();
        event.setUserId("physician");
        when(auditEventRepository.findByUserId("physician")).thenReturn(List.of(event));

        // When
        final List<AuditEvent> result = auditService.findByUserId("physician");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo("physician");
        verify(auditEventRepository).findByUserId("physician");
    }

    @Test
    void findByResourceType_shouldReturnEvents() {
        // Given
        final AuditEvent event = new AuditEvent();
        event.setResourceType("Patient");
        when(auditEventRepository.findByResourceType("Patient")).thenReturn(List.of(event));

        // When
        final List<AuditEvent> result = auditService.findByResourceType("Patient");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getResourceType()).isEqualTo("Patient");
        verify(auditEventRepository).findByResourceType("Patient");
    }
}
