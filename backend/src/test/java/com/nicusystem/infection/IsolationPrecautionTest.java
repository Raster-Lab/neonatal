package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IsolationPrecaution} entity.
 */
class IsolationPrecautionTest {

    @Test
    void setPatientId_shouldReturnSameValue() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        final UUID patientId = UUID.randomUUID();

        // When
        entity.setPatientId(patientId);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void setPrecautionType_shouldReturnSameValue() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();

        // When
        entity.setPrecautionType(
                IsolationPrecautionType.CONTACT);

        // Then
        assertThat(entity.getPrecautionType())
                .isEqualTo(IsolationPrecautionType.CONTACT);
    }

    @Test
    void setInitiatedAt_shouldReturnSameValue() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        final Instant now = Instant.now();

        // When
        entity.setInitiatedAt(now);

        // Then
        assertThat(entity.getInitiatedAt()).isEqualTo(now);
    }

    @Test
    void setDiscontinuedAt_shouldReturnSameValue() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        final Instant endTime = Instant.now();

        // When
        entity.setDiscontinuedAt(endTime);

        // Then
        assertThat(entity.getDiscontinuedAt()).isEqualTo(endTime);
    }

    @Test
    void setInitiatedBy_shouldReturnSameValue() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();

        // When
        entity.setInitiatedBy("Dr. Smith");

        // Then
        assertThat(entity.getInitiatedBy())
                .isEqualTo("Dr. Smith");
    }

    @Test
    void setIndication_shouldReturnSameValue() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();

        // When
        entity.setIndication("MRSA colonization");

        // Then
        assertThat(entity.getIndication())
                .isEqualTo("MRSA colonization");
    }

    @Test
    void setNotes_shouldReturnSameValue() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();

        // When
        entity.setNotes("Contact precautions initiated");

        // Then
        assertThat(entity.getNotes())
                .isEqualTo("Contact precautions initiated");
    }

    @Test
    void nullableFields_shouldDefaultToNull() {
        // Given / When
        final IsolationPrecaution entity =
                new IsolationPrecaution();

        // Then
        assertThat(entity.getDiscontinuedAt()).isNull();
        assertThat(entity.getInitiatedBy()).isNull();
        assertThat(entity.getIndication()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
