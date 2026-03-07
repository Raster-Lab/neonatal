package com.nicusystem.fluid;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FluidEntryMapper.
 */
class FluidEntryMapperTest {

    private final FluidEntryMapper mapper = new FluidEntryMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final FluidEntry entity = new FluidEntry();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setEntryType(FluidEntryType.INTAKE);
        entity.setCategory(FluidCategory.IV_FLUID);
        entity.setVolumeMl(50.0);
        entity.setDescription("TPN");
        entity.setRecordedAt(recordedAt);
        entity.setRecordedBy("nurse-001");

        // When
        final FluidEntryDto dto = mapper.toDto(entity);

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
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateFluidEntryRequest request = new CreateFluidEntryRequest(
                patientId, FluidEntryType.OUTPUT, FluidCategory.URINE,
                30.0, "catheter output", recordedAt, "nurse-002");

        // When
        final FluidEntry entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getEntryType()).isEqualTo(FluidEntryType.OUTPUT);
        assertThat(entity.getCategory()).isEqualTo(FluidCategory.URINE);
        assertThat(entity.getVolumeMl()).isEqualTo(30.0);
        assertThat(entity.getDescription()).isEqualTo("catheter output");
        assertThat(entity.getRecordedAt()).isEqualTo(recordedAt);
        assertThat(entity.getRecordedBy()).isEqualTo("nurse-002");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateFluidEntryRequest request = new CreateFluidEntryRequest(
                patientId, FluidEntryType.INTAKE, FluidCategory.BLOOD_PRODUCT,
                10.0, null, recordedAt, null);

        // When
        final FluidEntry entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getDescription()).isNull();
        assertThat(entity.getRecordedBy()).isNull();
    }
}
