package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FeedingOrderMapper}.
 */
class FeedingOrderMapperTest {

    private final FeedingOrderMapper mapper = new FeedingOrderMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final FeedingOrder entity = new FeedingOrder();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        entity.setPatientId(patientId);
        entity.setFeedingType(FeedingType.BREAST_MILK);
        entity.setFeedingRoute(FeedingRoute.NASOGASTRIC);
        entity.setVolumeMl(30.0);
        entity.setFrequencyHours(3);
        entity.setStartedAt(now);
        entity.setDiscontinuedAt(null);
        entity.setOrderedBy("Dr. Test");
        entity.setNotes("Test note");

        // When
        final FeedingOrderDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.feedingType()).isEqualTo(FeedingType.BREAST_MILK);
        assertThat(dto.feedingRoute()).isEqualTo(FeedingRoute.NASOGASTRIC);
        assertThat(dto.volumeMl()).isEqualTo(30.0);
        assertThat(dto.frequencyHours()).isEqualTo(3);
        assertThat(dto.startedAt()).isEqualTo(now);
        assertThat(dto.discontinuedAt()).isNull();
        assertThat(dto.orderedBy()).isEqualTo("Dr. Test");
        assertThat(dto.notes()).isEqualTo("Test note");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startedAt = Instant.now();
        final CreateFeedingOrderRequest request = new CreateFeedingOrderRequest(
                patientId, FeedingType.FORMULA, FeedingRoute.ORAL,
                20.0, 4, startedAt, "Dr. Mapper", "Mapper notes");

        // When
        final FeedingOrder entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getFeedingType()).isEqualTo(FeedingType.FORMULA);
        assertThat(entity.getFeedingRoute()).isEqualTo(FeedingRoute.ORAL);
        assertThat(entity.getVolumeMl()).isEqualTo(20.0);
        assertThat(entity.getFrequencyHours()).isEqualTo(4);
        assertThat(entity.getStartedAt()).isEqualTo(startedAt);
        assertThat(entity.getOrderedBy()).isEqualTo("Dr. Mapper");
        assertThat(entity.getNotes()).isEqualTo("Mapper notes");
    }

    @Test
    void toEntity_withNullOptionalFields_shouldMapNulls() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateFeedingOrderRequest request = new CreateFeedingOrderRequest(
                patientId, FeedingType.MIXED, FeedingRoute.TRANSPYLORIC,
                10.0, 6, null, null, null);

        // When
        final FeedingOrder entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getStartedAt()).isNull();
        assertThat(entity.getOrderedBy()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
