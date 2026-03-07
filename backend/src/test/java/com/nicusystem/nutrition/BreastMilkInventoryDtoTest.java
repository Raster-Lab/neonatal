package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BreastMilkInventoryDto} record.
 */
class BreastMilkInventoryDtoTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        final Instant expires = Instant.now();

        // When
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                id, patientId, "BMK-001", 60.0, now, expires, true, true,
                "Pasteurised", now, now);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.label()).isEqualTo("BMK-001");
        assertThat(dto.volumeMl()).isEqualTo(60.0);
        assertThat(dto.collectedAt()).isEqualTo(now);
        assertThat(dto.expiresAt()).isEqualTo(expires);
        assertThat(dto.donorMilk()).isTrue();
        assertThat(dto.fortified()).isTrue();
        assertThat(dto.notes()).isEqualTo("Pasteurised");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.updatedAt()).isEqualTo(now);
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                id, patientId, "BMK-002", 30.0, now, null, false, false,
                null, null, null);

        // Then
        assertThat(dto.expiresAt()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
        assertThat(dto.donorMilk()).isFalse();
        assertThat(dto.fortified()).isFalse();
    }
}
