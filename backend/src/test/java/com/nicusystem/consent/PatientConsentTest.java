package com.nicusystem.consent;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PatientConsent entity.
 */
class PatientConsentTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final PatientConsent consent = new PatientConsent();
        final UUID patientId = UUID.randomUUID();

        // When
        consent.setPatientId(patientId);

        // Then
        assertThat(consent.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetConsentType() {
        // Given
        final PatientConsent consent = new PatientConsent();

        // When
        consent.setConsentType(ConsentType.TREATMENT);

        // Then
        assertThat(consent.getConsentType()).isEqualTo(ConsentType.TREATMENT);
    }

    @Test
    void shouldSetAndGetConsentStatus() {
        // Given
        final PatientConsent consent = new PatientConsent();

        // When
        consent.setConsentStatus(ConsentStatus.GRANTED);

        // Then
        assertThat(consent.getConsentStatus()).isEqualTo(ConsentStatus.GRANTED);
    }

    @Test
    void shouldSetAndGetSignedBy() {
        // Given
        final PatientConsent consent = new PatientConsent();

        // When
        consent.setSignedBy("Jane Doe");

        // Then
        assertThat(consent.getSignedBy()).isEqualTo("Jane Doe");
    }

    @Test
    void shouldSetAndGetRelationship() {
        // Given
        final PatientConsent consent = new PatientConsent();

        // When
        consent.setRelationship("Mother");

        // Then
        assertThat(consent.getRelationship()).isEqualTo("Mother");
    }

    @Test
    void shouldSetAndGetSignedAt() {
        // Given
        final PatientConsent consent = new PatientConsent();
        final Instant now = Instant.now();

        // When
        consent.setSignedAt(now);

        // Then
        assertThat(consent.getSignedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetExpiresAt() {
        // Given
        final PatientConsent consent = new PatientConsent();
        final Instant future = Instant.now().plusSeconds(86400);

        // When
        consent.setExpiresAt(future);

        // Then
        assertThat(consent.getExpiresAt()).isEqualTo(future);
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final PatientConsent consent = new PatientConsent();

        // When
        consent.setNotes("Consent obtained verbally");

        // Then
        assertThat(consent.getNotes()).isEqualTo("Consent obtained verbally");
    }

    @Test
    void shouldSetAndGetActive() {
        // Given
        final PatientConsent consent = new PatientConsent();

        // When
        consent.setActive(false);

        // Then
        assertThat(consent.isActive()).isFalse();
    }

    @Test
    void shouldDefaultActiveTrueOnConstruction() {
        // Given / When
        final PatientConsent consent = new PatientConsent();

        // Then
        assertThat(consent.isActive()).isTrue();
    }
}
