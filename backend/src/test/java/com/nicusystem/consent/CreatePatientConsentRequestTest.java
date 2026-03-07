package com.nicusystem.consent;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreatePatientConsentRequest record.
 */
class CreatePatientConsentRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant signedAt = Instant.now();
        final Instant expiresAt = Instant.now().plusSeconds(86400);

        // When
        final CreatePatientConsentRequest request = new CreatePatientConsentRequest(
                patientId, ConsentType.RESEARCH_PARTICIPATION, ConsentStatus.GRANTED,
                "John Doe", "Father", signedAt, expiresAt, "notes");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.consentType()).isEqualTo(ConsentType.RESEARCH_PARTICIPATION);
        assertThat(request.consentStatus()).isEqualTo(ConsentStatus.GRANTED);
        assertThat(request.signedBy()).isEqualTo("John Doe");
        assertThat(request.relationship()).isEqualTo("Father");
        assertThat(request.signedAt()).isEqualTo(signedAt);
        assertThat(request.expiresAt()).isEqualTo(expiresAt);
        assertThat(request.notes()).isEqualTo("notes");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreatePatientConsentRequest request = new CreatePatientConsentRequest(
                patientId, ConsentType.TREATMENT, ConsentStatus.PENDING,
                null, null, null, null, null);

        // Then
        assertThat(request.signedBy()).isNull();
        assertThat(request.relationship()).isNull();
        assertThat(request.signedAt()).isNull();
        assertThat(request.expiresAt()).isNull();
        assertThat(request.notes()).isNull();
    }
}
