package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CreateIsolationPrecautionRequest} record.
 */
class CreateIsolationPrecautionRequestTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();

        // When
        final CreateIsolationPrecautionRequest request =
                new CreateIsolationPrecautionRequest(
                        patientId,
                        IsolationPrecautionType.CONTACT,
                        initiatedAt,
                        "Dr. Smith",
                        "MRSA colonization",
                        "Contact precautions");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.precautionType())
                .isEqualTo(IsolationPrecautionType.CONTACT);
        assertThat(request.initiatedAt())
                .isEqualTo(initiatedAt);
        assertThat(request.initiatedBy())
                .isEqualTo("Dr. Smith");
        assertThat(request.indication())
                .isEqualTo("MRSA colonization");
        assertThat(request.notes())
                .isEqualTo("Contact precautions");
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();

        // When
        final CreateIsolationPrecautionRequest request =
                new CreateIsolationPrecautionRequest(
                        patientId,
                        IsolationPrecautionType.AIRBORNE,
                        initiatedAt, null, null, null);

        // Then
        assertThat(request.initiatedBy()).isNull();
        assertThat(request.indication()).isNull();
        assertThat(request.notes()).isNull();
    }

    @Test
    void equality_sameValues_shouldBeEqual() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();

        // When
        final CreateIsolationPrecautionRequest r1 =
                new CreateIsolationPrecautionRequest(
                        patientId,
                        IsolationPrecautionType.DROPLET,
                        initiatedAt, "Dr. Jones",
                        "RSV", "Room 4");
        final CreateIsolationPrecautionRequest r2 =
                new CreateIsolationPrecautionRequest(
                        patientId,
                        IsolationPrecautionType.DROPLET,
                        initiatedAt, "Dr. Jones",
                        "RSV", "Room 4");

        // Then
        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }
}
