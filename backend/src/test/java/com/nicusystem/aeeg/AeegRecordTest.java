package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AeegRecord entity.
 */
class AeegRecordTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final AeegRecord record = new AeegRecord();
        final UUID patientId = UUID.randomUUID();

        // When
        record.setPatientId(patientId);

        // Then
        assertThat(record.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetClassification() {
        // Given
        final AeegRecord record = new AeegRecord();

        // When
        record.setClassification(AeegClassification.CONTINUOUS_NORMAL_VOLTAGE);

        // Then
        assertThat(record.getClassification()).isEqualTo(AeegClassification.CONTINUOUS_NORMAL_VOLTAGE);
    }

    @Test
    void shouldSetAndGetUpperMarginAmplitude() {
        // Given
        final AeegRecord record = new AeegRecord();

        // When
        record.setUpperMarginAmplitude(25.0);

        // Then
        assertThat(record.getUpperMarginAmplitude()).isEqualTo(25.0);
    }

    @Test
    void shouldSetAndGetLowerMarginAmplitude() {
        // Given
        final AeegRecord record = new AeegRecord();

        // When
        record.setLowerMarginAmplitude(7.0);

        // Then
        assertThat(record.getLowerMarginAmplitude()).isEqualTo(7.0);
    }

    @Test
    void shouldSetAndGetBandwidth() {
        // Given
        final AeegRecord record = new AeegRecord();

        // When
        record.setBandwidth(18.0);

        // Then
        assertThat(record.getBandwidth()).isEqualTo(18.0);
    }

    @Test
    void shouldSetAndGetSeizureDetected() {
        // Given
        final AeegRecord record = new AeegRecord();

        // When
        record.setSeizureDetected(true);

        // Then
        assertThat(record.isSeizureDetected()).isTrue();
    }

    @Test
    void shouldSetAndGetSeizureDurationSeconds() {
        // Given
        final AeegRecord record = new AeegRecord();

        // When
        record.setSeizureDurationSeconds(120);

        // Then
        assertThat(record.getSeizureDurationSeconds()).isEqualTo(120);
    }

    @Test
    void shouldSetAndGetRecordingStartTime() {
        // Given
        final AeegRecord record = new AeegRecord();
        final Instant now = Instant.now();

        // When
        record.setRecordingStartTime(now);

        // Then
        assertThat(record.getRecordingStartTime()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetRecordingEndTime() {
        // Given
        final AeegRecord record = new AeegRecord();
        final Instant now = Instant.now();

        // When
        record.setRecordingEndTime(now);

        // Then
        assertThat(record.getRecordingEndTime()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetDeviceIdentifier() {
        // Given
        final AeegRecord record = new AeegRecord();

        // When
        record.setDeviceIdentifier("BRM3-001");

        // Then
        assertThat(record.getDeviceIdentifier()).isEqualTo("BRM3-001");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final AeegRecord record = new AeegRecord();

        // When
        record.setNotes("Normal background activity");

        // Then
        assertThat(record.getNotes()).isEqualTo("Normal background activity");
    }
}
