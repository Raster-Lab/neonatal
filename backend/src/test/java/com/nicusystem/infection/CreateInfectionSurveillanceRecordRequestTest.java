package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CreateInfectionSurveillanceRecordRequest} record.
 */
class CreateInfectionSurveillanceRecordRequestTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();

        // When
        final CreateInfectionSurveillanceRecordRequest request =
                new CreateInfectionSurveillanceRecordRequest(
                        patientId,
                        InfectionSurveillanceType.CLABSI,
                        eventDate, true,
                        "Staphylococcus aureus",
                        7, 14, 5,
                        "Blood culture positive");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.surveillanceType())
                .isEqualTo(InfectionSurveillanceType.CLABSI);
        assertThat(request.eventDate()).isEqualTo(eventDate);
        assertThat(request.confirmed()).isTrue();
        assertThat(request.pathogen())
                .isEqualTo("Staphylococcus aureus");
        assertThat(request.antibioticDays()).isEqualTo(7);
        assertThat(request.centralLineDays()).isEqualTo(14);
        assertThat(request.ventilatorDays()).isEqualTo(5);
        assertThat(request.notes())
                .isEqualTo("Blood culture positive");
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();

        // When
        final CreateInfectionSurveillanceRecordRequest request =
                new CreateInfectionSurveillanceRecordRequest(
                        patientId,
                        InfectionSurveillanceType.VAE,
                        eventDate, false,
                        null, null, null, null, null);

        // Then
        assertThat(request.confirmed()).isFalse();
        assertThat(request.pathogen()).isNull();
        assertThat(request.antibioticDays()).isNull();
        assertThat(request.centralLineDays()).isNull();
        assertThat(request.ventilatorDays()).isNull();
        assertThat(request.notes()).isNull();
    }

    @Test
    void equality_sameValues_shouldBeEqual() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();

        // When
        final CreateInfectionSurveillanceRecordRequest r1 =
                new CreateInfectionSurveillanceRecordRequest(
                        patientId,
                        InfectionSurveillanceType.CAUTI,
                        eventDate, true,
                        "E. coli", 3, null, null, null);
        final CreateInfectionSurveillanceRecordRequest r2 =
                new CreateInfectionSurveillanceRecordRequest(
                        patientId,
                        InfectionSurveillanceType.CAUTI,
                        eventDate, true,
                        "E. coli", 3, null, null, null);

        // Then
        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }
}
