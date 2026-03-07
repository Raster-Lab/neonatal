package com.nicusystem.insurance;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PatientInsuranceDto record.
 */
class PatientInsuranceDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant subscriberDob = Instant.parse("1985-05-15T00:00:00Z");
        final Instant effectiveDate = Instant.now();
        final Instant terminationDate = Instant.now().plusSeconds(86400);

        // When
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                id, patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                "GRP456", "John Doe", subscriberDob, "Father",
                effectiveDate, terminationDate, "notes", true);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.insuranceType()).isEqualTo(InsuranceType.PRIMARY);
        assertThat(dto.insurerName()).isEqualTo("Blue Cross");
        assertThat(dto.policyNumber()).isEqualTo("POL123");
        assertThat(dto.groupNumber()).isEqualTo("GRP456");
        assertThat(dto.subscriberName()).isEqualTo("John Doe");
        assertThat(dto.subscriberDob()).isEqualTo(subscriberDob);
        assertThat(dto.relationshipToPatient()).isEqualTo("Father");
        assertThat(dto.effectiveDate()).isEqualTo(effectiveDate);
        assertThat(dto.terminationDate()).isEqualTo(terminationDate);
        assertThat(dto.notes()).isEqualTo("notes");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();

        // When
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                id, patientId, InsuranceType.SECONDARY, "Aetna", "POL789",
                null, null, null, null, null, null, null, true);

        // Then
        assertThat(dto.groupNumber()).isNull();
        assertThat(dto.subscriberName()).isNull();
        assertThat(dto.subscriberDob()).isNull();
        assertThat(dto.relationshipToPatient()).isNull();
        assertThat(dto.effectiveDate()).isNull();
        assertThat(dto.terminationDate()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
