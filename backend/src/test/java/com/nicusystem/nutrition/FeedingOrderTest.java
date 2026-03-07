package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FeedingOrder} entity setters and getters.
 */
class FeedingOrderTest {

    @Test
    void setPatientId_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();
        final UUID patientId = UUID.randomUUID();

        // When
        entity.setPatientId(patientId);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void setFeedingType_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();

        // When
        entity.setFeedingType(FeedingType.FORMULA);

        // Then
        assertThat(entity.getFeedingType()).isEqualTo(FeedingType.FORMULA);
    }

    @Test
    void setFeedingRoute_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();

        // When
        entity.setFeedingRoute(FeedingRoute.NASOGASTRIC);

        // Then
        assertThat(entity.getFeedingRoute()).isEqualTo(FeedingRoute.NASOGASTRIC);
    }

    @Test
    void setVolumeMl_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();

        // When
        entity.setVolumeMl(30.0);

        // Then
        assertThat(entity.getVolumeMl()).isEqualTo(30.0);
    }

    @Test
    void setFrequencyHours_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();

        // When
        entity.setFrequencyHours(3);

        // Then
        assertThat(entity.getFrequencyHours()).isEqualTo(3);
    }

    @Test
    void setStartedAt_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();
        final Instant now = Instant.now();

        // When
        entity.setStartedAt(now);

        // Then
        assertThat(entity.getStartedAt()).isEqualTo(now);
    }

    @Test
    void setDiscontinuedAt_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();
        final Instant now = Instant.now();

        // When
        entity.setDiscontinuedAt(now);

        // Then
        assertThat(entity.getDiscontinuedAt()).isEqualTo(now);
    }

    @Test
    void setOrderedBy_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();

        // When
        entity.setOrderedBy("Dr. Smith");

        // Then
        assertThat(entity.getOrderedBy()).isEqualTo("Dr. Smith");
    }

    @Test
    void setNotes_shouldReturnSameValue() {
        // Given
        final FeedingOrder entity = new FeedingOrder();

        // When
        entity.setNotes("Increase volume if tolerated");

        // Then
        assertThat(entity.getNotes()).isEqualTo("Increase volume if tolerated");
    }

    @Test
    void defaultValues_shouldBeNull() {
        // Given / When
        final FeedingOrder entity = new FeedingOrder();

        // Then
        assertThat(entity.getPatientId()).isNull();
        assertThat(entity.getFeedingType()).isNull();
        assertThat(entity.getFeedingRoute()).isNull();
        assertThat(entity.getVolumeMl()).isNull();
        assertThat(entity.getFrequencyHours()).isNull();
        assertThat(entity.getStartedAt()).isNull();
        assertThat(entity.getDiscontinuedAt()).isNull();
        assertThat(entity.getOrderedBy()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
