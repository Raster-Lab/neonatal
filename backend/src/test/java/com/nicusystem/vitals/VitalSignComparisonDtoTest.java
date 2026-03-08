package com.nicusystem.vitals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VitalSignComparisonDtoTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");
        final Instant bStart = Instant.parse("2024-01-14T00:00:00Z");
        final Instant bEnd = Instant.parse("2024-01-14T23:59:59Z");

        // When
        final VitalSignComparisonDto dto = new VitalSignComparisonDto(
                VitalSignType.HEART_RATE, 145.0, recordedAt,
                130.0, 110.0, 155.0, 20, bStart, bEnd, 11.538);

        // Then
        assertThat(dto.vitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(dto.currentValue()).isEqualTo(145.0);
        assertThat(dto.currentRecordedAt()).isEqualTo(recordedAt);
        assertThat(dto.baselineAvg()).isEqualTo(130.0);
        assertThat(dto.baselineMin()).isEqualTo(110.0);
        assertThat(dto.baselineMax()).isEqualTo(155.0);
        assertThat(dto.baselineCount()).isEqualTo(20);
        assertThat(dto.baselineStart()).isEqualTo(bStart);
        assertThat(dto.baselineEnd()).isEqualTo(bEnd);
        assertThat(dto.deviationPercent()).isEqualTo(11.538);
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");
        final Instant bStart = Instant.parse("2024-01-14T00:00:00Z");
        final Instant bEnd = Instant.parse("2024-01-14T23:59:59Z");
        final VitalSignComparisonDto dto1 = new VitalSignComparisonDto(
                VitalSignType.SPO2, 98.0, recordedAt,
                96.5, 92.0, 99.0, 5, bStart, bEnd, 1.55);
        final VitalSignComparisonDto dto2 = new VitalSignComparisonDto(
                VitalSignType.SPO2, 98.0, recordedAt,
                96.5, 92.0, 99.0, 5, bStart, bEnd, 1.55);

        // When & Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final VitalSignComparisonDto dto = new VitalSignComparisonDto(
                null, null, null, null, null, null, 0, null, null, null);

        // When & Then
        assertThat(dto.toString()).contains("VitalSignComparisonDto");
    }
}
