package com.nicusystem.vitals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for VitalSignAlarmThresholdMapper.
 */
class VitalSignAlarmThresholdMapperTest {

    private final VitalSignAlarmThresholdMapper mapper = new VitalSignAlarmThresholdMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final VitalSignAlarmThreshold entity = new VitalSignAlarmThreshold();
        final UUID id = UUID.randomUUID();
        entity.setId(id);
        entity.setVitalType(VitalSignType.HEART_RATE);
        entity.setMinimumGestationalAgeWeeks(24);
        entity.setMaximumGestationalAgeWeeks(36);
        entity.setMinimumWeightGrams(500);
        entity.setMaximumWeightGrams(2000);
        entity.setLowAlarmValue(100.0);
        entity.setHighAlarmValue(200.0);
        entity.setCriticalLowValue(80.0);
        entity.setCriticalHighValue(220.0);
        entity.setUnit("bpm");
        entity.setDescription("For premature infants");
        entity.setActive(true);

        // When
        final VitalSignAlarmThresholdDto dto = mapper.toDto(entity);

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
    void toEntity_shouldMapAllFields() {
        // Given
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.SPO2, 28, 40, 1000, 4000,
                        88.0, 100.0, 85.0, 100.0, "%", "SpO2 thresholds");

        // When
        final VitalSignAlarmThreshold entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getVitalType()).isEqualTo(VitalSignType.SPO2);
        assertThat(entity.getMinimumGestationalAgeWeeks()).isEqualTo(28);
        assertThat(entity.getMaximumGestationalAgeWeeks()).isEqualTo(40);
        assertThat(entity.getMinimumWeightGrams()).isEqualTo(1000);
        assertThat(entity.getMaximumWeightGrams()).isEqualTo(4000);
        assertThat(entity.getLowAlarmValue()).isEqualTo(88.0);
        assertThat(entity.getHighAlarmValue()).isEqualTo(100.0);
        assertThat(entity.getCriticalLowValue()).isEqualTo(85.0);
        assertThat(entity.getCriticalHighValue()).isEqualTo(100.0);
        assertThat(entity.getUnit()).isEqualTo("%");
        assertThat(entity.getDescription()).isEqualTo("SpO2 thresholds");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.TEMPERATURE, null, null, null, null,
                        null, null, null, null, "°C", null);

        // When
        final VitalSignAlarmThreshold entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getMinimumGestationalAgeWeeks()).isNull();
        assertThat(entity.getMaximumGestationalAgeWeeks()).isNull();
        assertThat(entity.getMinimumWeightGrams()).isNull();
        assertThat(entity.getMaximumWeightGrams()).isNull();
        assertThat(entity.getLowAlarmValue()).isNull();
        assertThat(entity.getHighAlarmValue()).isNull();
        assertThat(entity.getCriticalLowValue()).isNull();
        assertThat(entity.getCriticalHighValue()).isNull();
        assertThat(entity.getDescription()).isNull();
    }
}
