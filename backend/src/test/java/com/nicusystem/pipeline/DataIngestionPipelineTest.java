package com.nicusystem.pipeline;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DataIngestionPipeline entity.
 */
class DataIngestionPipelineTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();
        final UUID patientId = UUID.randomUUID();

        // When
        pipeline.setPatientId(patientId);

        // Then
        assertThat(pipeline.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetDataSource() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();

        // When
        pipeline.setDataSource(MonitorDataSource.CARDIAC_MONITOR);

        // Then
        assertThat(pipeline.getDataSource()).isEqualTo(MonitorDataSource.CARDIAC_MONITOR);
    }

    @Test
    void shouldSetAndGetDeviceIdentifier() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();

        // When
        pipeline.setDeviceIdentifier("MON-001");

        // Then
        assertThat(pipeline.getDeviceIdentifier()).isEqualTo("MON-001");
    }

    @Test
    void shouldSetAndGetStatus() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();

        // When
        pipeline.setStatus(PipelineStatus.ACTIVE);

        // Then
        assertThat(pipeline.getStatus()).isEqualTo(PipelineStatus.ACTIVE);
    }

    @Test
    void shouldSetAndGetConnectionEndpoint() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();

        // When
        pipeline.setConnectionEndpoint("tcp://192.168.1.100:5000");

        // Then
        assertThat(pipeline.getConnectionEndpoint()).isEqualTo("tcp://192.168.1.100:5000");
    }

    @Test
    void shouldSetAndGetPollingIntervalSeconds() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();

        // When
        pipeline.setPollingIntervalSeconds(5);

        // Then
        assertThat(pipeline.getPollingIntervalSeconds()).isEqualTo(5);
    }

    @Test
    void shouldSetAndGetLastDataReceivedAt() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();
        final Instant now = Instant.now();

        // When
        pipeline.setLastDataReceivedAt(now);

        // Then
        assertThat(pipeline.getLastDataReceivedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();

        // When
        pipeline.setNotes("Bedside monitor in Bay 3");

        // Then
        assertThat(pipeline.getNotes()).isEqualTo("Bedside monitor in Bay 3");
    }

    @Test
    void shouldAllowNullOptionalFields() {
        // Given
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();

        // When (no setters called for optional fields)

        // Then
        assertThat(pipeline.getLastDataReceivedAt()).isNull();
        assertThat(pipeline.getNotes()).isNull();
    }
}
