package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ApneaEventMapper.
 */
class ApneaEventMapperTest {

    private final ApneaEventMapper mapper = new ApneaEventMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final ApneaEvent entity = new ApneaEvent();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setOccurredAt(occurredAt);
        entity.setDurationSeconds(45);
        entity.setAssociatedBradycardia(true);
        entity.setLowestHeartRate(75);
        entity.setLowestSpo2(82.0);
        entity.setStimulationRequired(true);
        entity.setBaggingRequired(false);
        entity.setCaffeineDose(5.0);
        entity.setNotes("brief self-resolving");

        // When
        final ApneaEventDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.occurredAt()).isEqualTo(occurredAt);
        assertThat(dto.durationSeconds()).isEqualTo(45);
        assertThat(dto.associatedBradycardia()).isTrue();
        assertThat(dto.lowestHeartRate()).isEqualTo(75);
        assertThat(dto.lowestSpo2()).isEqualTo(82.0);
        assertThat(dto.stimulationRequired()).isTrue();
        assertThat(dto.baggingRequired()).isFalse();
        assertThat(dto.caffeineDose()).isEqualTo(5.0);
        assertThat(dto.notes()).isEqualTo("brief self-resolving");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();
        final CreateApneaEventRequest request = new CreateApneaEventRequest(
                patientId, occurredAt, 30, true, 80, 85.0, true, false, 5.0, "notes");

        // When
        final ApneaEvent entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getOccurredAt()).isEqualTo(occurredAt);
        assertThat(entity.getDurationSeconds()).isEqualTo(30);
        assertThat(entity.isAssociatedBradycardia()).isTrue();
        assertThat(entity.getLowestHeartRate()).isEqualTo(80);
        assertThat(entity.getLowestSpo2()).isEqualTo(85.0);
        assertThat(entity.isStimulationRequired()).isTrue();
        assertThat(entity.isBaggingRequired()).isFalse();
        assertThat(entity.getCaffeineDose()).isEqualTo(5.0);
        assertThat(entity.getNotes()).isEqualTo("notes");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();
        final CreateApneaEventRequest request = new CreateApneaEventRequest(
                patientId, occurredAt, null, false, null, null, false, false, null, null);

        // When
        final ApneaEvent entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getDurationSeconds()).isNull();
        assertThat(entity.isAssociatedBradycardia()).isFalse();
        assertThat(entity.getLowestHeartRate()).isNull();
        assertThat(entity.getLowestSpo2()).isNull();
        assertThat(entity.isStimulationRequired()).isFalse();
        assertThat(entity.isBaggingRequired()).isFalse();
        assertThat(entity.getCaffeineDose()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
