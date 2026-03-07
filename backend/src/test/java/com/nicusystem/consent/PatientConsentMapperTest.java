package com.nicusystem.consent;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PatientConsentMapper.
 */
class PatientConsentMapperTest {

    private final PatientConsentMapper mapper = new PatientConsentMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final PatientConsent entity = new PatientConsent();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant signedAt = Instant.now();
        final Instant expiresAt = Instant.now().plusSeconds(86400);
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setConsentType(ConsentType.TREATMENT);
        entity.setConsentStatus(ConsentStatus.GRANTED);
        entity.setSignedBy("Jane Doe");
        entity.setRelationship("Mother");
        entity.setSignedAt(signedAt);
        entity.setExpiresAt(expiresAt);
        entity.setNotes("test note");
        entity.setActive(true);

        // When
        final PatientConsentDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.consentType()).isEqualTo(ConsentType.TREATMENT);
        assertThat(dto.consentStatus()).isEqualTo(ConsentStatus.GRANTED);
        assertThat(dto.signedBy()).isEqualTo("Jane Doe");
        assertThat(dto.relationship()).isEqualTo("Mother");
        assertThat(dto.signedAt()).isEqualTo(signedAt);
        assertThat(dto.expiresAt()).isEqualTo(expiresAt);
        assertThat(dto.notes()).isEqualTo("test note");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant signedAt = Instant.now();
        final Instant expiresAt = Instant.now().plusSeconds(86400);
        final CreatePatientConsentRequest request = new CreatePatientConsentRequest(
                patientId, ConsentType.SURGERY, ConsentStatus.GRANTED,
                "John Doe", "Father", signedAt, expiresAt, "notes");

        // When
        final PatientConsent entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getConsentType()).isEqualTo(ConsentType.SURGERY);
        assertThat(entity.getConsentStatus()).isEqualTo(ConsentStatus.GRANTED);
        assertThat(entity.getSignedBy()).isEqualTo("John Doe");
        assertThat(entity.getRelationship()).isEqualTo("Father");
        assertThat(entity.getSignedAt()).isEqualTo(signedAt);
        assertThat(entity.getExpiresAt()).isEqualTo(expiresAt);
        assertThat(entity.getNotes()).isEqualTo("notes");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreatePatientConsentRequest request = new CreatePatientConsentRequest(
                patientId, ConsentType.TREATMENT, ConsentStatus.PENDING,
                null, null, null, null, null);

        // When
        final PatientConsent entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getSignedBy()).isNull();
        assertThat(entity.getRelationship()).isNull();
        assertThat(entity.getSignedAt()).isNull();
        assertThat(entity.getExpiresAt()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
