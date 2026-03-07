package com.nicusystem.fluid;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FluidEntry entity.
 */
class FluidEntryTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final FluidEntry entry = new FluidEntry();
        final UUID patientId = UUID.randomUUID();

        // When
        entry.setPatientId(patientId);

        // Then
        assertThat(entry.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetEntryType() {
        // Given
        final FluidEntry entry = new FluidEntry();

        // When
        entry.setEntryType(FluidEntryType.INTAKE);

        // Then
        assertThat(entry.getEntryType()).isEqualTo(FluidEntryType.INTAKE);
    }

    @Test
    void shouldSetAndGetCategory() {
        // Given
        final FluidEntry entry = new FluidEntry();

        // When
        entry.setCategory(FluidCategory.IV_FLUID);

        // Then
        assertThat(entry.getCategory()).isEqualTo(FluidCategory.IV_FLUID);
    }

    @Test
    void shouldSetAndGetVolumeMl() {
        // Given
        final FluidEntry entry = new FluidEntry();

        // When
        entry.setVolumeMl(50.0);

        // Then
        assertThat(entry.getVolumeMl()).isEqualTo(50.0);
    }

    @Test
    void shouldSetAndGetDescription() {
        // Given
        final FluidEntry entry = new FluidEntry();

        // When
        entry.setDescription("TPN infusion");

        // Then
        assertThat(entry.getDescription()).isEqualTo("TPN infusion");
    }

    @Test
    void shouldSetAndGetRecordedAt() {
        // Given
        final FluidEntry entry = new FluidEntry();
        final Instant now = Instant.now();

        // When
        entry.setRecordedAt(now);

        // Then
        assertThat(entry.getRecordedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetRecordedBy() {
        // Given
        final FluidEntry entry = new FluidEntry();

        // When
        entry.setRecordedBy("nurse-001");

        // Then
        assertThat(entry.getRecordedBy()).isEqualTo("nurse-001");
    }
}
