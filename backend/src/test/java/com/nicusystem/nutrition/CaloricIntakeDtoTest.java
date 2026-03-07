package com.nicusystem.nutrition;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CaloricIntakeDto} record.
 */
class CaloricIntakeDtoTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CaloricIntakeDto dto = new CaloricIntakeDto(
                patientId, "current", 163.2, 0.0, 163.2, 1000, 163.2);

        // Then
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.dateRange()).isEqualTo("current");
        assertThat(dto.enteralCalories()).isEqualTo(163.2);
        assertThat(dto.parenteralCalories()).isEqualTo(0.0);
        assertThat(dto.totalCalories()).isEqualTo(163.2);
        assertThat(dto.weightGrams()).isEqualTo(1000);
        assertThat(dto.caloriesPerKgPerDay()).isEqualTo(163.2);
    }

    @Test
    void constructor_withNullWeight_shouldAllowNull() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CaloricIntakeDto dto = new CaloricIntakeDto(
                patientId, "current", 0.0, 0.0, 0.0, null, 0.0);

        // Then
        assertThat(dto.weightGrams()).isNull();
        assertThat(dto.caloriesPerKgPerDay()).isEqualTo(0.0);
    }
}
