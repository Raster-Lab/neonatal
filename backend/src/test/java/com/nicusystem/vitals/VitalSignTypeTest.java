package com.nicusystem.vitals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VitalSignTypeTest {

    @Test
    void values_shouldContainAllTypes() {
        // Given & When
        final VitalSignType[] values = VitalSignType.values();

        // Then
        assertThat(values).containsExactly(
                VitalSignType.HEART_RATE,
                VitalSignType.RESPIRATORY_RATE,
                VitalSignType.SPO2,
                VitalSignType.BLOOD_PRESSURE_SYSTOLIC,
                VitalSignType.BLOOD_PRESSURE_DIASTOLIC,
                VitalSignType.TEMPERATURE,
                VitalSignType.MEAN_ARTERIAL_PRESSURE,
                VitalSignType.END_TIDAL_CO2,
                VitalSignType.PERFUSION_INDEX,
                VitalSignType.TRANSCUTANEOUS_BILIRUBIN
        );
    }

    @Test
    void valueOf_validName_shouldReturnEnum() {
        assertThat(VitalSignType.valueOf("HEART_RATE"))
                .isEqualTo(VitalSignType.HEART_RATE);
        assertThat(VitalSignType.valueOf("RESPIRATORY_RATE"))
                .isEqualTo(VitalSignType.RESPIRATORY_RATE);
        assertThat(VitalSignType.valueOf("SPO2"))
                .isEqualTo(VitalSignType.SPO2);
        assertThat(VitalSignType.valueOf("BLOOD_PRESSURE_SYSTOLIC"))
                .isEqualTo(VitalSignType.BLOOD_PRESSURE_SYSTOLIC);
        assertThat(VitalSignType.valueOf("BLOOD_PRESSURE_DIASTOLIC"))
                .isEqualTo(VitalSignType.BLOOD_PRESSURE_DIASTOLIC);
        assertThat(VitalSignType.valueOf("TEMPERATURE"))
                .isEqualTo(VitalSignType.TEMPERATURE);
        assertThat(VitalSignType.valueOf("MEAN_ARTERIAL_PRESSURE"))
                .isEqualTo(VitalSignType.MEAN_ARTERIAL_PRESSURE);
        assertThat(VitalSignType.valueOf("END_TIDAL_CO2"))
                .isEqualTo(VitalSignType.END_TIDAL_CO2);
        assertThat(VitalSignType.valueOf("PERFUSION_INDEX"))
                .isEqualTo(VitalSignType.PERFUSION_INDEX);
        assertThat(VitalSignType.valueOf("TRANSCUTANEOUS_BILIRUBIN"))
                .isEqualTo(VitalSignType.TRANSCUTANEOUS_BILIRUBIN);
    }
}
