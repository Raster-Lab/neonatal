package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CreateBreastMilkInventoryRequest} record.
 */
class CreateBreastMilkInventoryRequestTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant collectedAt = Instant.now();
        final Instant expiresAt = Instant.now();

        // When
        final CreateBreastMilkInventoryRequest request = new CreateBreastMilkInventoryRequest(
                patientId, "BMK-001", 50.0, collectedAt, expiresAt, true, true, "Donor notes");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.label()).isEqualTo("BMK-001");
        assertThat(request.volumeMl()).isEqualTo(50.0);
        assertThat(request.collectedAt()).isEqualTo(collectedAt);
        assertThat(request.expiresAt()).isEqualTo(expiresAt);
        assertThat(request.donorMilk()).isTrue();
        assertThat(request.fortified()).isTrue();
        assertThat(request.notes()).isEqualTo("Donor notes");
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant collectedAt = Instant.now();

        // When
        final CreateBreastMilkInventoryRequest request = new CreateBreastMilkInventoryRequest(
                patientId, "BMK-002", 30.0, collectedAt, null, false, false, null);

        // Then
        assertThat(request.expiresAt()).isNull();
        assertThat(request.notes()).isNull();
        assertThat(request.donorMilk()).isFalse();
        assertThat(request.fortified()).isFalse();
    }
}
