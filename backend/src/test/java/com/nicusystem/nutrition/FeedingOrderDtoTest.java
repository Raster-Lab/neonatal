package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FeedingOrderDto} record.
 */
class FeedingOrderDtoTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        final Instant discontinued = Instant.now();

        // When
        final FeedingOrderDto dto = new FeedingOrderDto(
                id, patientId, FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC,
                30.0, 3, now, discontinued, "Dr. Smith", "Test notes", now, now);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.feedingType()).isEqualTo(FeedingType.BREAST_MILK);
        assertThat(dto.feedingRoute()).isEqualTo(FeedingRoute.NASOGASTRIC);
        assertThat(dto.volumeMl()).isEqualTo(30.0);
        assertThat(dto.frequencyHours()).isEqualTo(3);
        assertThat(dto.startedAt()).isEqualTo(now);
        assertThat(dto.discontinuedAt()).isEqualTo(discontinued);
        assertThat(dto.orderedBy()).isEqualTo("Dr. Smith");
        assertThat(dto.notes()).isEqualTo("Test notes");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.updatedAt()).isEqualTo(now);
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();

        // When
        final FeedingOrderDto dto = new FeedingOrderDto(
                id, patientId, FeedingType.FORMULA, FeedingRoute.ORAL,
                20.0, 4, null, null, null, null, null, null);

        // Then
        assertThat(dto.startedAt()).isNull();
        assertThat(dto.discontinuedAt()).isNull();
        assertThat(dto.orderedBy()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }
}
