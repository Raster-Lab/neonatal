package com.nicusystem.infection;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InfectionSurveillanceType} enum.
 */
class InfectionSurveillanceTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final InfectionSurveillanceType[] values =
                InfectionSurveillanceType.values();

        // Then
        assertThat(values).containsExactly(
                InfectionSurveillanceType.CLABSI,
                InfectionSurveillanceType.VAE,
                InfectionSurveillanceType.CAUTI,
                InfectionSurveillanceType.SURGICAL_SITE_INFECTION,
                InfectionSurveillanceType.EARLY_ONSET_SEPSIS,
                InfectionSurveillanceType.LATE_ONSET_SEPSIS
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(InfectionSurveillanceType.valueOf("CLABSI"))
                .isEqualTo(InfectionSurveillanceType.CLABSI);
        assertThat(InfectionSurveillanceType.valueOf("VAE"))
                .isEqualTo(InfectionSurveillanceType.VAE);
        assertThat(InfectionSurveillanceType.valueOf("CAUTI"))
                .isEqualTo(InfectionSurveillanceType.CAUTI);
        assertThat(
                InfectionSurveillanceType.valueOf("SURGICAL_SITE_INFECTION"))
                .isEqualTo(
                        InfectionSurveillanceType.SURGICAL_SITE_INFECTION);
        assertThat(
                InfectionSurveillanceType.valueOf("EARLY_ONSET_SEPSIS"))
                .isEqualTo(
                        InfectionSurveillanceType.EARLY_ONSET_SEPSIS);
        assertThat(
                InfectionSurveillanceType.valueOf("LATE_ONSET_SEPSIS"))
                .isEqualTo(
                        InfectionSurveillanceType.LATE_ONSET_SEPSIS);
    }
}
