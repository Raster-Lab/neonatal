package com.nicusystem.vitals.autodoc;

import java.util.UUID;

import com.nicusystem.vitals.VitalSignType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutoDocConfigTest {

    @Test
    void noArgConstructor_shouldCreateInstance() {
        // Given & When
        final AutoDocConfig config = new AutoDocConfig();

        // Then
        assertThat(config).isNotNull();
        assertThat(config.isEnabled()).isTrue();
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // Given
        final AutoDocConfig config = new AutoDocConfig();
        final UUID patientId = UUID.randomUUID();

        // When
        config.setPatientId(patientId);
        config.setVitalType(VitalSignType.HEART_RATE);
        config.setInterval(AutoDocInterval.HOURLY);
        config.setEnabled(false);
        config.setNotes("Test notes");

        // Then
        assertThat(config.getPatientId()).isEqualTo(patientId);
        assertThat(config.getVitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(config.getInterval()).isEqualTo(AutoDocInterval.HOURLY);
        assertThat(config.isEnabled()).isFalse();
        assertThat(config.getNotes()).isEqualTo("Test notes");
    }

    @Test
    void settersAndGetters_nullOptionalFields_shouldAcceptNull() {
        // Given
        final AutoDocConfig config = new AutoDocConfig();

        // When
        config.setNotes(null);

        // Then
        assertThat(config.getNotes()).isNull();
    }
}
