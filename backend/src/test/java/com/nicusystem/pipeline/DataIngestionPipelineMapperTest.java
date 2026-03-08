package com.nicusystem.pipeline;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DataIngestionPipelineMapper.
 */
class DataIngestionPipelineMapperTest {

    private final DataIngestionPipelineMapper mapper = new DataIngestionPipelineMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final DataIngestionPipeline entity = new DataIngestionPipeline();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant lastReceived = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setDataSource(MonitorDataSource.PULSE_OXIMETER);
        entity.setDeviceIdentifier("OX-042");
        entity.setStatus(PipelineStatus.ACTIVE);
        entity.setConnectionEndpoint("tcp://192.168.1.50:4000");
        entity.setPollingIntervalSeconds(10);
        entity.setLastDataReceivedAt(lastReceived);
        entity.setNotes("SpO2 pipeline");

        // When
        final DataIngestionPipelineDto dto = mapper.toDto(entity);

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
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant lastReceived = Instant.now();
        final CreateDataIngestionPipelineRequest request = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.ACTIVE, "tcp://192.168.1.100:5000", 5,
                lastReceived, "Primary cardiac monitor");

        // When
        final DataIngestionPipeline entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getDataSource()).isEqualTo(MonitorDataSource.CARDIAC_MONITOR);
        assertThat(entity.getDeviceIdentifier()).isEqualTo("CM-001");
        assertThat(entity.getStatus()).isEqualTo(PipelineStatus.ACTIVE);
        assertThat(entity.getConnectionEndpoint()).isEqualTo("tcp://192.168.1.100:5000");
        assertThat(entity.getPollingIntervalSeconds()).isEqualTo(5);
        assertThat(entity.getLastDataReceivedAt()).isEqualTo(lastReceived);
        assertThat(entity.getNotes()).isEqualTo("Primary cardiac monitor");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateDataIngestionPipelineRequest request = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.VENTILATOR, "VENT-007",
                PipelineStatus.DISCONNECTED, "tcp://10.0.0.1:3000", 30,
                null, null);

        // When
        final DataIngestionPipeline entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getLastDataReceivedAt()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
