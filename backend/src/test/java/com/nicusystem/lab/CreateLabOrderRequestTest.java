package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateLabOrderRequest record.
 */
class CreateLabOrderRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final CreateLabOrderRequest request = new CreateLabOrderRequest(
                patientId, LabPanelType.COMPLETE_BLOOD_COUNT, now, "Dr. Smith", 200.0, "stat");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.panelType()).isEqualTo(LabPanelType.COMPLETE_BLOOD_COUNT);
        assertThat(request.orderedAt()).isEqualTo(now);
        assertThat(request.orderedBy()).isEqualTo("Dr. Smith");
        assertThat(request.specimenVolumeUl()).isEqualTo(200.0);
        assertThat(request.notes()).isEqualTo("stat");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreateLabOrderRequest request = new CreateLabOrderRequest(
                patientId, LabPanelType.BILIRUBIN_PANEL, null, null, null, null);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.panelType()).isEqualTo(LabPanelType.BILIRUBIN_PANEL);
        assertThat(request.orderedAt()).isNull();
        assertThat(request.orderedBy()).isNull();
        assertThat(request.specimenVolumeUl()).isNull();
        assertThat(request.notes()).isNull();
    }
}
