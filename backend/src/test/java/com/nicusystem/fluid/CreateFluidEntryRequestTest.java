package com.nicusystem.fluid;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateFluidEntryRequest record.
 */
class CreateFluidEntryRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final CreateFluidEntryRequest request = new CreateFluidEntryRequest(
                patientId, FluidEntryType.INTAKE, FluidCategory.ORAL_FEED,
                20.0, "breast milk", recordedAt, "nurse-001");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.entryType()).isEqualTo(FluidEntryType.INTAKE);
        assertThat(request.category()).isEqualTo(FluidCategory.ORAL_FEED);
        assertThat(request.volumeMl()).isEqualTo(20.0);
        assertThat(request.description()).isEqualTo("breast milk");
        assertThat(request.recordedAt()).isEqualTo(recordedAt);
        assertThat(request.recordedBy()).isEqualTo("nurse-001");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final CreateFluidEntryRequest request = new CreateFluidEntryRequest(
                patientId, FluidEntryType.OUTPUT, FluidCategory.URINE,
                15.0, null, recordedAt, null);

        // Then
        assertThat(request.description()).isNull();
        assertThat(request.recordedBy()).isNull();
    }
}
