package com.nicusystem.fluid;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FluidEntryDto record.
 */
class FluidEntryDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final FluidEntryDto dto = new FluidEntryDto(
                id, patientId, FluidEntryType.INTAKE, FluidCategory.IV_FLUID,
                50.0, "TPN", recordedAt, "nurse-001");

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.entryType()).isEqualTo(FluidEntryType.INTAKE);
        assertThat(dto.category()).isEqualTo(FluidCategory.IV_FLUID);
        assertThat(dto.volumeMl()).isEqualTo(50.0);
        assertThat(dto.description()).isEqualTo("TPN");
        assertThat(dto.recordedAt()).isEqualTo(recordedAt);
        assertThat(dto.recordedBy()).isEqualTo("nurse-001");
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final FluidEntryDto dto = new FluidEntryDto(
                id, patientId, FluidEntryType.OUTPUT, FluidCategory.URINE,
                30.0, null, recordedAt, null);

        // Then
        assertThat(dto.description()).isNull();
        assertThat(dto.recordedBy()).isNull();
    }
}
