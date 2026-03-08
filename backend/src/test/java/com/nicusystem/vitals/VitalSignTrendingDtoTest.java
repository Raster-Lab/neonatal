package com.nicusystem.vitals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VitalSignTrendingDtoTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");

        // When
        final VitalSignTrendingDto dto = new VitalSignTrendingDto(
                VitalSignType.HEART_RATE, 100.0, 160.0, 135.5, 10, start, end);

        // Then
        assertThat(dto.vitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(dto.minValue()).isEqualTo(100.0);
        assertThat(dto.maxValue()).isEqualTo(160.0);
        assertThat(dto.avgValue()).isEqualTo(135.5);
        assertThat(dto.count()).isEqualTo(10);
        assertThat(dto.periodStart()).isEqualTo(start);
        assertThat(dto.periodEnd()).isEqualTo(end);
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSignTrendingDto dto1 = new VitalSignTrendingDto(
                VitalSignType.SPO2, 92.0, 99.0, 96.5, 5, start, end);
        final VitalSignTrendingDto dto2 = new VitalSignTrendingDto(
                VitalSignType.SPO2, 92.0, 99.0, 96.5, 5, start, end);

        // When & Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final VitalSignTrendingDto dto = new VitalSignTrendingDto(
                null, null, null, null, 0, null, null);

        // When & Then
        assertThat(dto.toString()).contains("VitalSignTrendingDto");
    }
}
