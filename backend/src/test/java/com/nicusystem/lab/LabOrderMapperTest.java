package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LabOrderMapper.
 */
class LabOrderMapperTest {

    private final LabOrderMapper mapper = new LabOrderMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final LabOrder entity = new LabOrder();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setPanelType(LabPanelType.COMPLETE_BLOOD_COUNT);
        entity.setStatus(LabOrderStatus.ORDERED);
        entity.setOrderedAt(now);
        entity.setOrderedBy("Dr. Smith");
        entity.setCollectedAt(now);
        entity.setCollectedBy("Nurse Jones");
        entity.setSpecimenVolumeUl(200.0);
        entity.setNotes("urgent");

        // When
        final LabOrderDto dto = mapper.toDto(entity);

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
    }

    @Test
    void toEntity_shouldMapAllFieldsWithOrderedStatus() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        final CreateLabOrderRequest request = new CreateLabOrderRequest(
                patientId, LabPanelType.BILIRUBIN_PANEL, now, "Dr. Brown", 150.0, "stat");

        // When
        final LabOrder entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getPanelType()).isEqualTo(LabPanelType.BILIRUBIN_PANEL);
        assertThat(entity.getStatus()).isEqualTo(LabOrderStatus.ORDERED);
        assertThat(entity.getOrderedAt()).isEqualTo(now);
        assertThat(entity.getOrderedBy()).isEqualTo("Dr. Brown");
        assertThat(entity.getSpecimenVolumeUl()).isEqualTo(150.0);
        assertThat(entity.getNotes()).isEqualTo("stat");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateLabOrderRequest request = new CreateLabOrderRequest(
                patientId, LabPanelType.BLOOD_CULTURE, null, null, null, null);

        // When
        final LabOrder entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getOrderedAt()).isNull();
        assertThat(entity.getOrderedBy()).isNull();
        assertThat(entity.getSpecimenVolumeUl()).isNull();
        assertThat(entity.getNotes()).isNull();
        assertThat(entity.getStatus()).isEqualTo(LabOrderStatus.ORDERED);
    }
}
