package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for RespiratoryRecord entity.
 */
class RespiratoryRecordTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();
        final UUID patientId = UUID.randomUUID();

        // When
        record.setPatientId(patientId);

        // Then
        assertThat(record.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetSupportMode() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setSupportMode(RespiratorySupport.CPAP);

        // Then
        assertThat(record.getSupportMode()).isEqualTo(RespiratorySupport.CPAP);
    }

    @Test
    void shouldSetAndGetFio2Percent() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setFio2Percent(40.0);

        // Then
        assertThat(record.getFio2Percent()).isEqualTo(40.0);
    }

    @Test
    void shouldSetAndGetPeep() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setPeep(5.0);

        // Then
        assertThat(record.getPeep()).isEqualTo(5.0);
    }

    @Test
    void shouldSetAndGetPip() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setPip(22.0);

        // Then
        assertThat(record.getPip()).isEqualTo(22.0);
    }

    @Test
    void shouldSetAndGetRatePerMin() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setRatePerMin(40);

        // Then
        assertThat(record.getRatePerMin()).isEqualTo(40);
    }

    @Test
    void shouldSetAndGetTiSeconds() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setTiSeconds(0.35);

        // Then
        assertThat(record.getTiSeconds()).isEqualTo(0.35);
    }

    @Test
    void shouldSetAndGetMapCmh2o() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setMapCmh2o(10.0);

        // Then
        assertThat(record.getMapCmh2o()).isEqualTo(10.0);
    }

    @Test
    void shouldSetAndGetFlowLpm() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setFlowLpm(6.0);

        // Then
        assertThat(record.getFlowLpm()).isEqualTo(6.0);
    }

    @Test
    void shouldSetAndGetRecordedAt() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();
        final Instant now = Instant.now();

        // When
        record.setRecordedAt(now);

        // Then
        assertThat(record.getRecordedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetRecordedBy() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setRecordedBy("nurse1");

        // Then
        assertThat(record.getRecordedBy()).isEqualTo("nurse1");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final RespiratoryRecord record = new RespiratoryRecord();

        // When
        record.setNotes("On CPAP day 3");

        // Then
        assertThat(record.getNotes()).isEqualTo("On CPAP day 3");
    }
}
