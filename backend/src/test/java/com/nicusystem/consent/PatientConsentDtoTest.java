package com.nicusystem.consent;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PatientConsentDto record.
 */
class PatientConsentDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant signedAt = Instant.now();
        final Instant expiresAt = Instant.now().plusSeconds(86400);

        // When
        final PatientConsentDto dto = new PatientConsentDto(
                id, patientId, ConsentType.TREATMENT, ConsentStatus.GRANTED,
                "Jane Doe", "Mother", signedAt, expiresAt, "notes", true);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.consentType()).isEqualTo(ConsentType.TREATMENT);
        assertThat(dto.consentStatus()).isEqualTo(ConsentStatus.GRANTED);
        assertThat(dto.signedBy()).isEqualTo("Jane Doe");
        assertThat(dto.relationship()).isEqualTo("Mother");
        assertThat(dto.signedAt()).isEqualTo(signedAt);
        assertThat(dto.expiresAt()).isEqualTo(expiresAt);
        assertThat(dto.notes()).isEqualTo("notes");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();

        // When
        final PatientConsentDto dto = new PatientConsentDto(
                id, patientId, ConsentType.SURGERY, ConsentStatus.PENDING,
                null, null, null, null, null, true);

        // Then
        assertThat(dto.signedBy()).isNull();
        assertThat(dto.relationship()).isNull();
        assertThat(dto.signedAt()).isNull();
        assertThat(dto.expiresAt()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
