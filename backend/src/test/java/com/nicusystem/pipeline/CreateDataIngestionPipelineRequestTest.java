package com.nicusystem.pipeline;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateDataIngestionPipelineRequest record.
 */
class CreateDataIngestionPipelineRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant lastReceived = Instant.now();

        // When
        final CreateDataIngestionPipelineRequest request = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.ACTIVE, "tcp://192.168.1.100:5000", 5,
                lastReceived, "Primary cardiac monitor");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.dataSource()).isEqualTo(MonitorDataSource.CARDIAC_MONITOR);
        assertThat(request.deviceIdentifier()).isEqualTo("CM-001");
        assertThat(request.status()).isEqualTo(PipelineStatus.ACTIVE);
        assertThat(request.connectionEndpoint()).isEqualTo("tcp://192.168.1.100:5000");
        assertThat(request.pollingIntervalSeconds()).isEqualTo(5);
        assertThat(request.lastDataReceivedAt()).isEqualTo(lastReceived);
        assertThat(request.notes()).isEqualTo("Primary cardiac monitor");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreateDataIngestionPipelineRequest request = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.TEMPERATURE_PROBE, "TP-003",
                PipelineStatus.ACTIVE, "tcp://10.0.0.2:4000", 10,
                null, null);

        // Then
        assertThat(request.lastDataReceivedAt()).isNull();
        assertThat(request.notes()).isNull();
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateDataIngestionPipelineRequest request1 = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.VENTILATOR, "VENT-001",
                PipelineStatus.ACTIVE, "tcp://10.0.0.1:3000", 15,
                null, null);
        final CreateDataIngestionPipelineRequest request2 = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.VENTILATOR, "VENT-001",
                PipelineStatus.ACTIVE, "tcp://10.0.0.1:3000", 15,
                null, null);

        // When / Then
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateDataIngestionPipelineRequest request = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.INFUSION_PUMP, "IP-005",
                PipelineStatus.PAUSED, "tcp://10.0.0.3:7000", 20,
                null, "Test pump");

        // When
        final String result = request.toString();

        // Then
        assertThat(result).contains("INFUSION_PUMP");
        assertThat(result).contains("IP-005");
    }
}
