package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InfectionSurveillanceRecordMapper}.
 */
class InfectionSurveillanceRecordMapperTest {

    private final InfectionSurveillanceRecordMapper mapper =
            new InfectionSurveillanceRecordMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();
        entity.setPatientId(patientId);
        entity.setSurveillanceType(
                InfectionSurveillanceType.CLABSI);
        entity.setEventDate(eventDate);
        entity.setConfirmed(true);
        entity.setPathogen("Staphylococcus aureus");
        entity.setAntibioticDays(7);
        entity.setCentralLineDays(14);
        entity.setVentilatorDays(5);
        entity.setNotes("Blood culture positive");

        // When
        final InfectionSurveillanceRecordDto dto =
                mapper.toDto(entity);

        // Then
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.surveillanceType())
                .isEqualTo(InfectionSurveillanceType.CLABSI);
        assertThat(dto.eventDate()).isEqualTo(eventDate);
        assertThat(dto.confirmed()).isTrue();
        assertThat(dto.pathogen())
                .isEqualTo("Staphylococcus aureus");
        assertThat(dto.antibioticDays()).isEqualTo(7);
        assertThat(dto.centralLineDays()).isEqualTo(14);
        assertThat(dto.ventilatorDays()).isEqualTo(5);
        assertThat(dto.notes())
                .isEqualTo("Blood culture positive");
    }

    @Test
    void toDto_confirmedFalse_shouldMapCorrectly() {
        // Given
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();
        entity.setPatientId(UUID.randomUUID());
        entity.setSurveillanceType(
                InfectionSurveillanceType.VAE);
        entity.setEventDate(Instant.now());
        entity.setConfirmed(false);

        // When
        final InfectionSurveillanceRecordDto dto =
                mapper.toDto(entity);

        // Then
        assertThat(dto.confirmed()).isFalse();
        assertThat(dto.pathogen()).isNull();
        assertThat(dto.antibioticDays()).isNull();
        assertThat(dto.centralLineDays()).isNull();
        assertThat(dto.ventilatorDays()).isNull();
        assertThat(dto.notes()).isNull();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();
        final CreateInfectionSurveillanceRecordRequest request =
                new CreateInfectionSurveillanceRecordRequest(
                        patientId,
                        InfectionSurveillanceType.CAUTI,
                        eventDate, true,
                        "E. coli", 5, 10, 3,
                        "Catheter-related");

        // When
        final InfectionSurveillanceRecord entity =
                mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getSurveillanceType())
                .isEqualTo(InfectionSurveillanceType.CAUTI);
        assertThat(entity.getEventDate()).isEqualTo(eventDate);
        assertThat(entity.isConfirmed()).isTrue();
        assertThat(entity.getPathogen()).isEqualTo("E. coli");
        assertThat(entity.getAntibioticDays()).isEqualTo(5);
        assertThat(entity.getCentralLineDays()).isEqualTo(10);
        assertThat(entity.getVentilatorDays()).isEqualTo(3);
        assertThat(entity.getNotes())
                .isEqualTo("Catheter-related");
    }

    @Test
    void toEntity_withNullOptionalFields_shouldMapCorrectly() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateInfectionSurveillanceRecordRequest request =
                new CreateInfectionSurveillanceRecordRequest(
                        patientId,
                        InfectionSurveillanceType.EARLY_ONSET_SEPSIS,
                        Instant.now(), false,
                        null, null, null, null, null);

        // When
        final InfectionSurveillanceRecord entity =
                mapper.toEntity(request);

        // Then
        assertThat(entity.isConfirmed()).isFalse();
        assertThat(entity.getPathogen()).isNull();
        assertThat(entity.getAntibioticDays()).isNull();
        assertThat(entity.getCentralLineDays()).isNull();
        assertThat(entity.getVentilatorDays()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
