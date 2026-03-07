package com.nicusystem.vitals;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VitalSignTest {

    @Test
    void noArgConstructor_shouldCreateInstance() {
        // Given & When
        final VitalSign vitalSign = new VitalSign();

        // Then
        assertThat(vitalSign).isNotNull();
        assertThat(vitalSign.isManualEntry()).isFalse();
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // Given
        final VitalSign vitalSign = new VitalSign();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        vitalSign.setPatientId(patientId);
        vitalSign.setVitalType(VitalSignType.HEART_RATE);
        vitalSign.setValue(140.0);
        vitalSign.setUnit("bpm");
        vitalSign.setRecordedAt(now);
        vitalSign.setTemperatureSite(TemperatureSite.AXILLARY);
        vitalSign.setManualEntry(true);
        vitalSign.setNotes("Normal range");

        // Then
        assertThat(vitalSign.getPatientId()).isEqualTo(patientId);
        assertThat(vitalSign.getVitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(vitalSign.getValue()).isEqualTo(140.0);
        assertThat(vitalSign.getUnit()).isEqualTo("bpm");
        assertThat(vitalSign.getRecordedAt()).isEqualTo(now);
        assertThat(vitalSign.getTemperatureSite()).isEqualTo(TemperatureSite.AXILLARY);
        assertThat(vitalSign.isManualEntry()).isTrue();
        assertThat(vitalSign.getNotes()).isEqualTo("Normal range");
    }

    @Test
    void settersAndGetters_nullOptionalFields_shouldAcceptNull() {
        // Given
        final VitalSign vitalSign = new VitalSign();

        // When
        vitalSign.setTemperatureSite(null);
        vitalSign.setNotes(null);

        // Then
        assertThat(vitalSign.getTemperatureSite()).isNull();
        assertThat(vitalSign.getNotes()).isNull();
    }
}
