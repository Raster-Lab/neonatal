package com.nicusystem.fluid;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FluidBalanceSummaryDto record.
 */
class FluidBalanceSummaryDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.now();
        final Instant end = Instant.now().plusSeconds(86400);

        // When
        final FluidBalanceSummaryDto dto = new FluidBalanceSummaryDto(
                patientId, start, end,
                500.0, 400.0, 100.0,
                1200, 416.67, 333.33);

        // Then
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.periodStart()).isEqualTo(start);
        assertThat(dto.periodEnd()).isEqualTo(end);
        assertThat(dto.totalIntakeMl()).isEqualTo(500.0);
        assertThat(dto.totalOutputMl()).isEqualTo(400.0);
        assertThat(dto.netBalanceMl()).isEqualTo(100.0);
        assertThat(dto.patientWeightGrams()).isEqualTo(1200);
        assertThat(dto.intakePerKgPerDay()).isEqualTo(416.67);
        assertThat(dto.outputPerKgPerDay()).isEqualTo(333.33);
    }

    @Test
    void shouldCreateDtoWithNullWeightFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.now();
        final Instant end = Instant.now().plusSeconds(86400);

        // When
        final FluidBalanceSummaryDto dto = new FluidBalanceSummaryDto(
                patientId, start, end,
                300.0, 250.0, 50.0,
                null, null, null);

        // Then
        assertThat(dto.patientWeightGrams()).isNull();
        assertThat(dto.intakePerKgPerDay()).isNull();
        assertThat(dto.outputPerKgPerDay()).isNull();
    }
}
