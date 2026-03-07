package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InfectionSurveillanceRecord} entity.
 */
class InfectionSurveillanceRecordTest {

    @Test
    void setPatientId_shouldReturnSameValue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();
        final UUID patientId = UUID.randomUUID();

        // When
        entity.setPatientId(patientId);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void setSurveillanceType_shouldReturnSameValue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // When
        entity.setSurveillanceType(
                InfectionSurveillanceType.CLABSI);

        // Then
        assertThat(entity.getSurveillanceType())
                .isEqualTo(InfectionSurveillanceType.CLABSI);
    }

    @Test
    void setEventDate_shouldReturnSameValue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();
        final Instant now = Instant.now();

        // When
        entity.setEventDate(now);

        // Then
        assertThat(entity.getEventDate()).isEqualTo(now);
    }

    @Test
    void setConfirmed_true_isConfirmedReturnsTrue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // When
        entity.setConfirmed(true);

        // Then
        assertThat(entity.isConfirmed()).isTrue();
    }

    @Test
    void setConfirmed_false_isConfirmedReturnsFalse() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // When
        entity.setConfirmed(false);

        // Then
        assertThat(entity.isConfirmed()).isFalse();
    }

    @Test
    void defaultConfirmedValue_shouldBeFalse() {
        // Given / When
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // Then
        assertThat(entity.isConfirmed()).isFalse();
    }

    @Test
    void setPathogen_shouldReturnSameValue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // When
        entity.setPathogen("Staphylococcus aureus");

        // Then
        assertThat(entity.getPathogen())
                .isEqualTo("Staphylococcus aureus");
    }

    @Test
    void setAntibioticDays_shouldReturnSameValue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // When
        entity.setAntibioticDays(7);

        // Then
        assertThat(entity.getAntibioticDays()).isEqualTo(7);
    }

    @Test
    void setCentralLineDays_shouldReturnSameValue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // When
        entity.setCentralLineDays(14);

        // Then
        assertThat(entity.getCentralLineDays()).isEqualTo(14);
    }

    @Test
    void setVentilatorDays_shouldReturnSameValue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // When
        entity.setVentilatorDays(5);

        // Then
        assertThat(entity.getVentilatorDays()).isEqualTo(5);
    }

    @Test
    void setNotes_shouldReturnSameValue() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // When
        entity.setNotes("Blood culture positive");

        // Then
        assertThat(entity.getNotes())
                .isEqualTo("Blood culture positive");
    }

    @Test
    void nullableFields_shouldDefaultToNull() {
        // Given / When
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();

        // Then
        assertThat(entity.getPathogen()).isNull();
        assertThat(entity.getAntibioticDays()).isNull();
        assertThat(entity.getCentralLineDays()).isNull();
        assertThat(entity.getVentilatorDays()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
