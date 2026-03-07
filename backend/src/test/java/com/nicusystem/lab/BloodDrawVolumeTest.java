package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for BloodDrawVolume entity.
 */
class BloodDrawVolumeTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final BloodDrawVolume draw = new BloodDrawVolume();
        final UUID patientId = UUID.randomUUID();

        // When
        draw.setPatientId(patientId);

        // Then
        assertThat(draw.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetLabOrderId() {
        // Given
        final BloodDrawVolume draw = new BloodDrawVolume();
        final UUID labOrderId = UUID.randomUUID();

        // When
        draw.setLabOrderId(labOrderId);

        // Then
        assertThat(draw.getLabOrderId()).isEqualTo(labOrderId);
    }

    @Test
    void shouldSetAndGetDrawnAt() {
        // Given
        final BloodDrawVolume draw = new BloodDrawVolume();
        final Instant now = Instant.now();

        // When
        draw.setDrawnAt(now);

        // Then
        assertThat(draw.getDrawnAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetVolumeUl() {
        // Given
        final BloodDrawVolume draw = new BloodDrawVolume();

        // When
        draw.setVolumeUl(300.0);

        // Then
        assertThat(draw.getVolumeUl()).isEqualTo(300.0);
    }

    @Test
    void shouldSetAndGetDrawnBy() {
        // Given
        final BloodDrawVolume draw = new BloodDrawVolume();

        // When
        draw.setDrawnBy("Nurse Jones");

        // Then
        assertThat(draw.getDrawnBy()).isEqualTo("Nurse Jones");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final BloodDrawVolume draw = new BloodDrawVolume();

        // When
        draw.setNotes("difficult heel stick");

        // Then
        assertThat(draw.getNotes()).isEqualTo("difficult heel stick");
    }
}
