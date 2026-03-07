package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateLabResultRequest record.
 */
class CreateLabResultRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final CreateLabResultRequest request = new CreateLabResultRequest(
                labOrderId, patientId, "Hemoglobin", "12.5", "g/dL",
                "14.0", "20.0", true, true, now, "Lab Tech", "low");

        // Then
        assertThat(request.labOrderId()).isEqualTo(labOrderId);
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.testName()).isEqualTo("Hemoglobin");
        assertThat(request.resultValue()).isEqualTo("12.5");
        assertThat(request.unit()).isEqualTo("g/dL");
        assertThat(request.referenceRangeLow()).isEqualTo("14.0");
        assertThat(request.referenceRangeHigh()).isEqualTo("20.0");
        assertThat(request.isCritical()).isTrue();
        assertThat(request.isAbnormal()).isTrue();
        assertThat(request.resultedAt()).isEqualTo(now);
        assertThat(request.resultedBy()).isEqualTo("Lab Tech");
        assertThat(request.notes()).isEqualTo("low");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();

        // When
        final CreateLabResultRequest request = new CreateLabResultRequest(
                labOrderId, patientId, "WBC", "8.5", null,
                null, null, false, false, null, null, null);

        // Then
        assertThat(request.unit()).isNull();
        assertThat(request.referenceRangeLow()).isNull();
        assertThat(request.referenceRangeHigh()).isNull();
        assertThat(request.isCritical()).isFalse();
        assertThat(request.isAbnormal()).isFalse();
        assertThat(request.resultedAt()).isNull();
        assertThat(request.resultedBy()).isNull();
        assertThat(request.notes()).isNull();
    }
}
