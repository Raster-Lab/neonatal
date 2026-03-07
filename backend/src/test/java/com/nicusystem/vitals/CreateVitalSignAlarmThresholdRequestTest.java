package com.nicusystem.vitals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateVitalSignAlarmThresholdRequest record.
 */
class CreateVitalSignAlarmThresholdRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given / When
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.TEMPERATURE, 28, 40, 1000, 4000,
                        36.0, 38.0, 35.0, 39.0, "°C", "Standard temperature thresholds");

        // Then
        assertThat(request.vitalType()).isEqualTo(VitalSignType.TEMPERATURE);
        assertThat(request.minimumGestationalAgeWeeks()).isEqualTo(28);
        assertThat(request.maximumGestationalAgeWeeks()).isEqualTo(40);
        assertThat(request.minimumWeightGrams()).isEqualTo(1000);
        assertThat(request.maximumWeightGrams()).isEqualTo(4000);
        assertThat(request.lowAlarmValue()).isEqualTo(36.0);
        assertThat(request.highAlarmValue()).isEqualTo(38.0);
        assertThat(request.criticalLowValue()).isEqualTo(35.0);
        assertThat(request.criticalHighValue()).isEqualTo(39.0);
        assertThat(request.unit()).isEqualTo("°C");
        assertThat(request.description()).isEqualTo("Standard temperature thresholds");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given / When
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.SPO2, null, null, null, null,
                        null, null, null, null, "%", null);

        // Then
        assertThat(request.minimumGestationalAgeWeeks()).isNull();
        assertThat(request.maximumGestationalAgeWeeks()).isNull();
        assertThat(request.minimumWeightGrams()).isNull();
        assertThat(request.maximumWeightGrams()).isNull();
        assertThat(request.lowAlarmValue()).isNull();
        assertThat(request.highAlarmValue()).isNull();
        assertThat(request.criticalLowValue()).isNull();
        assertThat(request.criticalHighValue()).isNull();
        assertThat(request.description()).isNull();
    }
}
