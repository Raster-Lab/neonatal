package com.nicusystem.flowsheet;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FlowsheetEntry entity.
 */
class FlowsheetEntryTest {

    @Test
    void shouldCreateWithNoArgConstructor() {
        // Given / When
        final FlowsheetEntry entry = new FlowsheetEntry();

        // Then
        assertThat(entry).isNotNull();
    }

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final FlowsheetEntry entry = new FlowsheetEntry();
        final UUID patientId = UUID.randomUUID();

        // When
        entry.setPatientId(patientId);

        // Then
        assertThat(entry.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetCategory() {
        // Given
        final FlowsheetEntry entry = new FlowsheetEntry();

        // When
        entry.setCategory(FlowsheetCategory.VITAL_SIGNS);

        // Then
        assertThat(entry.getCategory())
                .isEqualTo(FlowsheetCategory.VITAL_SIGNS);
    }

    @Test
    void shouldSetAndGetEntryTime() {
        // Given
        final FlowsheetEntry entry = new FlowsheetEntry();
        final Instant now = Instant.now();

        // When
        entry.setEntryTime(now);

        // Then
        assertThat(entry.getEntryTime()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetFieldName() {
        // Given
        final FlowsheetEntry entry = new FlowsheetEntry();

        // When
        entry.setFieldName("heart_rate");

        // Then
        assertThat(entry.getFieldName()).isEqualTo("heart_rate");
    }

    @Test
    void shouldSetAndGetFieldValue() {
        // Given
        final FlowsheetEntry entry = new FlowsheetEntry();

        // When
        entry.setFieldValue("145");

        // Then
        assertThat(entry.getFieldValue()).isEqualTo("145");
    }

    @Test
    void shouldSetAndGetDocumentedBy() {
        // Given
        final FlowsheetEntry entry = new FlowsheetEntry();

        // When
        entry.setDocumentedBy("nurse-001");

        // Then
        assertThat(entry.getDocumentedBy()).isEqualTo("nurse-001");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final FlowsheetEntry entry = new FlowsheetEntry();

        // When
        entry.setNotes("Stable vitals");

        // Then
        assertThat(entry.getNotes()).isEqualTo("Stable vitals");
    }
}
