package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ApneaEventDto record.
 */
class ApneaEventDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final ApneaEventDto dto = new ApneaEventDto(
                id, patientId, occurredAt, 30, true, 75, 82.0,
                true, false, 5.0, "brief episode", createdAt, updatedAt);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.occurredAt()).isEqualTo(occurredAt);
        assertThat(dto.durationSeconds()).isEqualTo(30);
        assertThat(dto.associatedBradycardia()).isTrue();
        assertThat(dto.lowestHeartRate()).isEqualTo(75);
        assertThat(dto.lowestSpo2()).isEqualTo(82.0);
        assertThat(dto.stimulationRequired()).isTrue();
        assertThat(dto.baggingRequired()).isFalse();
        assertThat(dto.caffeineDose()).isEqualTo(5.0);
        assertThat(dto.notes()).isEqualTo("brief episode");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();

        // When
        final ApneaEventDto dto = new ApneaEventDto(
                id, patientId, occurredAt, null, false, null, null,
                false, false, null, null, null, null);

        // Then
        assertThat(dto.durationSeconds()).isNull();
        assertThat(dto.associatedBradycardia()).isFalse();
        assertThat(dto.lowestHeartRate()).isNull();
        assertThat(dto.lowestSpo2()).isNull();
        assertThat(dto.stimulationRequired()).isFalse();
        assertThat(dto.baggingRequired()).isFalse();
        assertThat(dto.caffeineDose()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
