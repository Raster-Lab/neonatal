package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InfectionSurveillanceRecordDto} record.
 */
class InfectionSurveillanceRecordDtoTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        id, patientId,
                        InfectionSurveillanceType.CLABSI,
                        eventDate, true,
                        "Staphylococcus aureus",
                        7, 14, 5,
                        "Blood culture positive",
                        createdAt, updatedAt);

        // Then
        assertThat(dto.id()).isEqualTo(id);
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
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();

        // When
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        id, patientId,
                        InfectionSurveillanceType.VAE,
                        eventDate, false,
                        null, null, null, null, null,
                        null, null);

        // Then
        assertThat(dto.confirmed()).isFalse();
        assertThat(dto.pathogen()).isNull();
        assertThat(dto.antibioticDays()).isNull();
        assertThat(dto.centralLineDays()).isNull();
        assertThat(dto.ventilatorDays()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }

    @Test
    void equality_sameValues_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();

        // When
        final InfectionSurveillanceRecordDto dto1 =
                new InfectionSurveillanceRecordDto(
                        id, patientId,
                        InfectionSurveillanceType.CAUTI,
                        eventDate, true, "E. coli",
                        5, null, null, null, null, null);
        final InfectionSurveillanceRecordDto dto2 =
                new InfectionSurveillanceRecordDto(
                        id, patientId,
                        InfectionSurveillanceType.CAUTI,
                        eventDate, true, "E. coli",
                        5, null, null, null, null, null);

        // Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}
