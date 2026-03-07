package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LabResult entity.
 */
class LabResultTest {

    @Test
    void shouldSetAndGetLabOrderId() {
        // Given
        final LabResult result = new LabResult();
        final UUID labOrderId = UUID.randomUUID();

        // When
        result.setLabOrderId(labOrderId);

        // Then
        assertThat(result.getLabOrderId()).isEqualTo(labOrderId);
    }

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final LabResult result = new LabResult();
        final UUID patientId = UUID.randomUUID();

        // When
        result.setPatientId(patientId);

        // Then
        assertThat(result.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetTestName() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setTestName("Hemoglobin");

        // Then
        assertThat(result.getTestName()).isEqualTo("Hemoglobin");
    }

    @Test
    void shouldSetAndGetResultValue() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setResultValue("12.5");

        // Then
        assertThat(result.getResultValue()).isEqualTo("12.5");
    }

    @Test
    void shouldSetAndGetUnit() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setUnit("g/dL");

        // Then
        assertThat(result.getUnit()).isEqualTo("g/dL");
    }

    @Test
    void shouldSetAndGetReferenceRangeLow() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setReferenceRangeLow("14.0");

        // Then
        assertThat(result.getReferenceRangeLow()).isEqualTo("14.0");
    }

    @Test
    void shouldSetAndGetReferenceRangeHigh() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setReferenceRangeHigh("20.0");

        // Then
        assertThat(result.getReferenceRangeHigh()).isEqualTo("20.0");
    }

    @Test
    void shouldSetAndGetIsCritical() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setCritical(true);

        // Then
        assertThat(result.isCritical()).isTrue();
    }

    @Test
    void shouldSetAndGetIsAbnormal() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setAbnormal(true);

        // Then
        assertThat(result.isAbnormal()).isTrue();
    }

    @Test
    void shouldSetAndGetResultedAt() {
        // Given
        final LabResult result = new LabResult();
        final Instant now = Instant.now();

        // When
        result.setResultedAt(now);

        // Then
        assertThat(result.getResultedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetResultedBy() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setResultedBy("Lab Tech");

        // Then
        assertThat(result.getResultedBy()).isEqualTo("Lab Tech");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final LabResult result = new LabResult();

        // When
        result.setNotes("critical value called");

        // Then
        assertThat(result.getNotes()).isEqualTo("critical value called");
    }
}
