package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CreateFeedingOrderRequest} record.
 */
class CreateFeedingOrderRequestTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startedAt = Instant.now();

        // When
        final CreateFeedingOrderRequest request = new CreateFeedingOrderRequest(
                patientId, FeedingType.DONOR_MILK, FeedingRoute.OROGASTRIC,
                25.0, 2, startedAt, "Dr. Jones", "Fortified feed");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.feedingType()).isEqualTo(FeedingType.DONOR_MILK);
        assertThat(request.feedingRoute()).isEqualTo(FeedingRoute.OROGASTRIC);
        assertThat(request.volumeMl()).isEqualTo(25.0);
        assertThat(request.frequencyHours()).isEqualTo(2);
        assertThat(request.startedAt()).isEqualTo(startedAt);
        assertThat(request.orderedBy()).isEqualTo("Dr. Jones");
        assertThat(request.notes()).isEqualTo("Fortified feed");
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreateFeedingOrderRequest request = new CreateFeedingOrderRequest(
                patientId, FeedingType.FORMULA, FeedingRoute.ORAL,
                15.0, 3, null, null, null);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.startedAt()).isNull();
        assertThat(request.orderedBy()).isNull();
        assertThat(request.notes()).isNull();
    }
}
