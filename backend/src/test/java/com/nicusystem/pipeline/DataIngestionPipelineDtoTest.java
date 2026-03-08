package com.nicusystem.pipeline;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DataIngestionPipelineDto record.
 */
class DataIngestionPipelineDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant lastReceived = Instant.now();

        // When
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                id, patientId, MonitorDataSource.PULSE_OXIMETER, "OX-042",
                PipelineStatus.ACTIVE, "tcp://192.168.1.50:4000", 10,
                lastReceived, "SpO2 pipeline");

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.dataSource()).isEqualTo(MonitorDataSource.PULSE_OXIMETER);
        assertThat(dto.deviceIdentifier()).isEqualTo("OX-042");
        assertThat(dto.status()).isEqualTo(PipelineStatus.ACTIVE);
        assertThat(dto.connectionEndpoint()).isEqualTo("tcp://192.168.1.50:4000");
        assertThat(dto.pollingIntervalSeconds()).isEqualTo(10);
        assertThat(dto.lastDataReceivedAt()).isEqualTo(lastReceived);
        assertThat(dto.notes()).isEqualTo("SpO2 pipeline");
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();

        // When
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                id, patientId, MonitorDataSource.VENTILATOR, "VENT-007",
                PipelineStatus.DISCONNECTED, "tcp://10.0.0.1:3000", 30,
                null, null);

        // Then
        assertThat(dto.lastDataReceivedAt()).isNull();
        assertThat(dto.notes()).isNull();
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant lastReceived = Instant.now();
        final DataIngestionPipelineDto dto1 = new DataIngestionPipelineDto(
                id, patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.ACTIVE, "tcp://10.0.0.1:5000", 5,
                lastReceived, "notes");
        final DataIngestionPipelineDto dto2 = new DataIngestionPipelineDto(
                id, patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.ACTIVE, "tcp://10.0.0.1:5000", 5,
                lastReceived, "notes");

        // When / Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                id, patientId, MonitorDataSource.EEG_MONITOR, "EEG-010",
                PipelineStatus.PAUSED, "tcp://10.0.0.5:6000", 15,
                null, null);

        // When
        final String result = dto.toString();

        // Then
        assertThat(result).contains("EEG_MONITOR");
        assertThat(result).contains("EEG-010");
    }
}
