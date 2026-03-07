package com.nicusystem.lab;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LabPanelType enum.
 */
class LabPanelTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final LabPanelType[] values = LabPanelType.values();

        // Then
        assertThat(values).containsExactly(
                LabPanelType.COMPLETE_BLOOD_COUNT,
                LabPanelType.BLOOD_GAS_ARTERIAL,
                LabPanelType.BLOOD_GAS_VENOUS,
                LabPanelType.BLOOD_GAS_CAPILLARY,
                LabPanelType.BASIC_METABOLIC_PANEL,
                LabPanelType.COMPREHENSIVE_METABOLIC_PANEL,
                LabPanelType.COAGULATION_STUDIES,
                LabPanelType.BILIRUBIN_PANEL,
                LabPanelType.BLOOD_CULTURE,
                LabPanelType.CRP_PROCALCITONIN,
                LabPanelType.NEWBORN_METABOLIC_SCREEN,
                LabPanelType.DRUG_LEVEL,
                LabPanelType.THYROID_FUNCTION,
                LabPanelType.AMMONIA_LACTATE_PYRUVATE,
                LabPanelType.CRANIAL_ULTRASOUND,
                LabPanelType.ECHOCARDIOGRAM,
                LabPanelType.HEARING_SCREEN,
                LabPanelType.RETINOPATHY_SCREEN
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(LabPanelType.valueOf("COMPLETE_BLOOD_COUNT"))
                .isEqualTo(LabPanelType.COMPLETE_BLOOD_COUNT);
        assertThat(LabPanelType.valueOf("BILIRUBIN_PANEL"))
                .isEqualTo(LabPanelType.BILIRUBIN_PANEL);
        assertThat(LabPanelType.valueOf("RETINOPATHY_SCREEN"))
                .isEqualTo(LabPanelType.RETINOPATHY_SCREEN);
    }
}
