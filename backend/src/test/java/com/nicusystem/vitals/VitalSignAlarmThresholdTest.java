package com.nicusystem.vitals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for VitalSignAlarmThreshold entity.
 */
class VitalSignAlarmThresholdTest {

    @Test
    void shouldSetAndGetVitalType() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setVitalType(VitalSignType.HEART_RATE);

        // Then
        assertThat(threshold.getVitalType()).isEqualTo(VitalSignType.HEART_RATE);
    }

    @Test
    void shouldSetAndGetMinimumGestationalAgeWeeks() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setMinimumGestationalAgeWeeks(24);

        // Then
        assertThat(threshold.getMinimumGestationalAgeWeeks()).isEqualTo(24);
    }

    @Test
    void shouldSetAndGetMaximumGestationalAgeWeeks() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setMaximumGestationalAgeWeeks(36);

        // Then
        assertThat(threshold.getMaximumGestationalAgeWeeks()).isEqualTo(36);
    }

    @Test
    void shouldSetAndGetMinimumWeightGrams() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setMinimumWeightGrams(500);

        // Then
        assertThat(threshold.getMinimumWeightGrams()).isEqualTo(500);
    }

    @Test
    void shouldSetAndGetMaximumWeightGrams() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setMaximumWeightGrams(2000);

        // Then
        assertThat(threshold.getMaximumWeightGrams()).isEqualTo(2000);
    }

    @Test
    void shouldSetAndGetLowAlarmValue() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setLowAlarmValue(100.0);

        // Then
        assertThat(threshold.getLowAlarmValue()).isEqualTo(100.0);
    }

    @Test
    void shouldSetAndGetHighAlarmValue() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setHighAlarmValue(200.0);

        // Then
        assertThat(threshold.getHighAlarmValue()).isEqualTo(200.0);
    }

    @Test
    void shouldSetAndGetCriticalLowValue() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setCriticalLowValue(80.0);

        // Then
        assertThat(threshold.getCriticalLowValue()).isEqualTo(80.0);
    }

    @Test
    void shouldSetAndGetCriticalHighValue() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setCriticalHighValue(220.0);

        // Then
        assertThat(threshold.getCriticalHighValue()).isEqualTo(220.0);
    }

    @Test
    void shouldSetAndGetUnit() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setUnit("bpm");

        // Then
        assertThat(threshold.getUnit()).isEqualTo("bpm");
    }

    @Test
    void shouldSetAndGetDescription() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setDescription("For premature infants");

        // Then
        assertThat(threshold.getDescription()).isEqualTo("For premature infants");
    }

    @Test
    void shouldSetAndGetActive() {
        // Given
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // When
        threshold.setActive(false);

        // Then
        assertThat(threshold.isActive()).isFalse();
    }

    @Test
    void shouldDefaultActiveTrueOnConstruction() {
        // Given / When
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();

        // Then
        assertThat(threshold.isActive()).isTrue();
    }
}
