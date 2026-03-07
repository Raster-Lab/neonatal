package com.nicusystem.vitals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for VitalSignAlarmThresholdDto record.
 */
class VitalSignAlarmThresholdDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();

        // When
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                id, VitalSignType.HEART_RATE, 24, 36, 500, 2000,
                100.0, 200.0, 80.0, 220.0, "bpm", "For premature infants", true);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.vitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(dto.minimumGestationalAgeWeeks()).isEqualTo(24);
        assertThat(dto.maximumGestationalAgeWeeks()).isEqualTo(36);
        assertThat(dto.minimumWeightGrams()).isEqualTo(500);
        assertThat(dto.maximumWeightGrams()).isEqualTo(2000);
        assertThat(dto.lowAlarmValue()).isEqualTo(100.0);
        assertThat(dto.highAlarmValue()).isEqualTo(200.0);
        assertThat(dto.criticalLowValue()).isEqualTo(80.0);
        assertThat(dto.criticalHighValue()).isEqualTo(220.0);
        assertThat(dto.unit()).isEqualTo("bpm");
        assertThat(dto.description()).isEqualTo("For premature infants");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();

        // When
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                id, VitalSignType.SPO2, null, null, null, null,
                null, null, null, null, "%", null, true);

        // Then
        assertThat(dto.minimumGestationalAgeWeeks()).isNull();
        assertThat(dto.maximumGestationalAgeWeeks()).isNull();
        assertThat(dto.minimumWeightGrams()).isNull();
        assertThat(dto.maximumWeightGrams()).isNull();
        assertThat(dto.lowAlarmValue()).isNull();
        assertThat(dto.highAlarmValue()).isNull();
        assertThat(dto.criticalLowValue()).isNull();
        assertThat(dto.criticalHighValue()).isNull();
        assertThat(dto.description()).isNull();
    }
}
