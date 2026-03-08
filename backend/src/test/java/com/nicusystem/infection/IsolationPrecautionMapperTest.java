package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IsolationPrecautionMapper}.
 */
class IsolationPrecautionMapperTest {

    private final IsolationPrecautionMapper mapper =
            new IsolationPrecautionMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();
        final Instant discontinuedAt = Instant.now();
        entity.setPatientId(patientId);
        entity.setPrecautionType(
                IsolationPrecautionType.CONTACT);
        entity.setInitiatedAt(initiatedAt);
        entity.setDiscontinuedAt(discontinuedAt);
        entity.setInitiatedBy("Dr. Smith");
        entity.setIndication("MRSA colonization");
        entity.setNotes("Contact precautions");

        // When
        final IsolationPrecautionDto dto = mapper.toDto(entity);

        // Then
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
    }

    @Test
    void toDto_withNullOptionalFields_shouldMapCorrectly() {
        // Given
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        entity.setPatientId(UUID.randomUUID());
        entity.setPrecautionType(
                IsolationPrecautionType.AIRBORNE);
        entity.setInitiatedAt(Instant.now());

        // When
        final IsolationPrecautionDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.discontinuedAt()).isNull();
        assertThat(dto.initiatedBy()).isNull();
        assertThat(dto.indication()).isNull();
        assertThat(dto.notes()).isNull();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();
        final CreateIsolationPrecautionRequest request =
                new CreateIsolationPrecautionRequest(
                        patientId,
                        IsolationPrecautionType.DROPLET,
                        initiatedAt, "Dr. Jones",
                        "RSV infection", "Room 4");

        // When
        final IsolationPrecaution entity =
                mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getPrecautionType())
                .isEqualTo(IsolationPrecautionType.DROPLET);
        assertThat(entity.getInitiatedAt())
                .isEqualTo(initiatedAt);
        assertThat(entity.getInitiatedBy())
                .isEqualTo("Dr. Jones");
        assertThat(entity.getIndication())
                .isEqualTo("RSV infection");
        assertThat(entity.getNotes()).isEqualTo("Room 4");
    }

    @Test
    void toEntity_withNullOptionalFields_shouldMapCorrectly() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateIsolationPrecautionRequest request =
                new CreateIsolationPrecautionRequest(
                        patientId,
                        IsolationPrecautionType.STANDARD,
                        Instant.now(),
                        null, null, null);

        // When
        final IsolationPrecaution entity =
                mapper.toEntity(request);

        // Then
        assertThat(entity.getInitiatedBy()).isNull();
        assertThat(entity.getIndication()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
