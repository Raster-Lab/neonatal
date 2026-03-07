package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LabOrder entity.
 */
class LabOrderTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final LabOrder order = new LabOrder();
        final UUID patientId = UUID.randomUUID();

        // When
        order.setPatientId(patientId);

        // Then
        assertThat(order.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetPanelType() {
        // Given
        final LabOrder order = new LabOrder();

        // When
        order.setPanelType(LabPanelType.COMPLETE_BLOOD_COUNT);

        // Then
        assertThat(order.getPanelType()).isEqualTo(LabPanelType.COMPLETE_BLOOD_COUNT);
    }

    @Test
    void shouldSetAndGetStatus() {
        // Given
        final LabOrder order = new LabOrder();

        // When
        order.setStatus(LabOrderStatus.ORDERED);

        // Then
        assertThat(order.getStatus()).isEqualTo(LabOrderStatus.ORDERED);
    }

    @Test
    void shouldSetAndGetOrderedAt() {
        // Given
        final LabOrder order = new LabOrder();
        final Instant now = Instant.now();

        // When
        order.setOrderedAt(now);

        // Then
        assertThat(order.getOrderedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetOrderedBy() {
        // Given
        final LabOrder order = new LabOrder();

        // When
        order.setOrderedBy("Dr. Smith");

        // Then
        assertThat(order.getOrderedBy()).isEqualTo("Dr. Smith");
    }

    @Test
    void shouldSetAndGetCollectedAt() {
        // Given
        final LabOrder order = new LabOrder();
        final Instant now = Instant.now();

        // When
        order.setCollectedAt(now);

        // Then
        assertThat(order.getCollectedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetCollectedBy() {
        // Given
        final LabOrder order = new LabOrder();

        // When
        order.setCollectedBy("Nurse Jones");

        // Then
        assertThat(order.getCollectedBy()).isEqualTo("Nurse Jones");
    }

    @Test
    void shouldSetAndGetSpecimenVolumeUl() {
        // Given
        final LabOrder order = new LabOrder();

        // When
        order.setSpecimenVolumeUl(200.0);

        // Then
        assertThat(order.getSpecimenVolumeUl()).isEqualTo(200.0);
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final LabOrder order = new LabOrder();

        // When
        order.setNotes("urgent lab draw");

        // Then
        assertThat(order.getNotes()).isEqualTo("urgent lab draw");
    }
}
