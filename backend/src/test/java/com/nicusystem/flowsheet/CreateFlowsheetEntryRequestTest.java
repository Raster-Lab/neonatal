package com.nicusystem.flowsheet;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateFlowsheetEntryRequest record.
 */
class CreateFlowsheetEntryRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();

        // When
        final CreateFlowsheetEntryRequest request =
                new CreateFlowsheetEntryRequest(
                        patientId, FlowsheetCategory.INTAKE_OUTPUT,
                        entryTime, "iv_fluid_in", "25",
                        "nurse-001", "D10W running");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.category())
                .isEqualTo(FlowsheetCategory.INTAKE_OUTPUT);
        assertThat(request.entryTime()).isEqualTo(entryTime);
        assertThat(request.fieldName()).isEqualTo("iv_fluid_in");
        assertThat(request.fieldValue()).isEqualTo("25");
        assertThat(request.documentedBy()).isEqualTo("nurse-001");
        assertThat(request.notes()).isEqualTo("D10W running");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();

        // When
        final CreateFlowsheetEntryRequest request =
                new CreateFlowsheetEntryRequest(
                        patientId, FlowsheetCategory.INTERVENTION,
                        entryTime, "suction", "Performed",
                        "nurse-001", null);

        // Then
        assertThat(request.notes()).isNull();
    }
}
