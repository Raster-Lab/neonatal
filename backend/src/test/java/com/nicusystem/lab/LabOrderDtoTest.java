package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LabOrderDto record.
 */
class LabOrderDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final LabOrderDto dto = new LabOrderDto(
                id, patientId, LabPanelType.COMPLETE_BLOOD_COUNT, LabOrderStatus.ORDERED,
                now, "Dr. Smith", now, "Nurse Jones", 200.0, "urgent", now, now);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.panelType()).isEqualTo(LabPanelType.COMPLETE_BLOOD_COUNT);
        assertThat(dto.status()).isEqualTo(LabOrderStatus.ORDERED);
        assertThat(dto.orderedAt()).isEqualTo(now);
        assertThat(dto.orderedBy()).isEqualTo("Dr. Smith");
        assertThat(dto.collectedAt()).isEqualTo(now);
        assertThat(dto.collectedBy()).isEqualTo("Nurse Jones");
        assertThat(dto.specimenVolumeUl()).isEqualTo(200.0);
        assertThat(dto.notes()).isEqualTo("urgent");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.updatedAt()).isEqualTo(now);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final LabOrderDto dto = new LabOrderDto(
                id, patientId, LabPanelType.BLOOD_CULTURE, LabOrderStatus.CANCELLED,
                null, null, null, null, null, null, now, now);

        // Then
        assertThat(dto.orderedAt()).isNull();
        assertThat(dto.orderedBy()).isNull();
        assertThat(dto.collectedAt()).isNull();
        assertThat(dto.collectedBy()).isNull();
        assertThat(dto.specimenVolumeUl()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
