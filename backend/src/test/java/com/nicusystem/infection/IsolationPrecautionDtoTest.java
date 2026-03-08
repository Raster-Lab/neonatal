package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IsolationPrecautionDto} record.
 */
class IsolationPrecautionDtoTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();
        final Instant discontinuedAt = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        id, patientId,
                        IsolationPrecautionType.CONTACT,
                        initiatedAt, discontinuedAt,
                        "Dr. Smith",
                        "MRSA colonization",
                        "Contact precautions",
                        createdAt, updatedAt);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.precautionType())
                .isEqualTo(IsolationPrecautionType.CONTACT);
        assertThat(dto.initiatedAt()).isEqualTo(initiatedAt);
        assertThat(dto.discontinuedAt())
                .isEqualTo(discontinuedAt);
        assertThat(dto.initiatedBy()).isEqualTo("Dr. Smith");
        assertThat(dto.indication())
                .isEqualTo("MRSA colonization");
        assertThat(dto.notes())
                .isEqualTo("Contact precautions");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();

        // When
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        id, patientId,
                        IsolationPrecautionType.DROPLET,
                        initiatedAt, null,
                        null, null, null, null, null);

        // Then
        assertThat(dto.discontinuedAt()).isNull();
        assertThat(dto.initiatedBy()).isNull();
        assertThat(dto.indication()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }

    @Test
    void equality_sameValues_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();

        // When
        final IsolationPrecautionDto dto1 =
                new IsolationPrecautionDto(
                        id, patientId,
                        IsolationPrecautionType.AIRBORNE,
                        initiatedAt, null,
                        "Dr. Jones", "TB suspect",
                        null, null, null);
        final IsolationPrecautionDto dto2 =
                new IsolationPrecautionDto(
                        id, patientId,
                        IsolationPrecautionType.AIRBORNE,
                        initiatedAt, null,
                        "Dr. Jones", "TB suspect",
                        null, null, null);

        // Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}
