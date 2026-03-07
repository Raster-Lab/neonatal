package com.nicusystem.insurance;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreatePatientInsuranceRequest record.
 */
class CreatePatientInsuranceRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant subscriberDob = Instant.parse("1985-05-15T00:00:00Z");
        final Instant effectiveDate = Instant.now();
        final Instant terminationDate = Instant.now().plusSeconds(86400);

        // When
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                "GRP456", "John Doe", subscriberDob, "Father",
                effectiveDate, terminationDate, "notes");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.insuranceType()).isEqualTo(InsuranceType.PRIMARY);
        assertThat(request.insurerName()).isEqualTo("Blue Cross");
        assertThat(request.policyNumber()).isEqualTo("POL123");
        assertThat(request.groupNumber()).isEqualTo("GRP456");
        assertThat(request.subscriberName()).isEqualTo("John Doe");
        assertThat(request.subscriberDob()).isEqualTo(subscriberDob);
        assertThat(request.relationshipToPatient()).isEqualTo("Father");
        assertThat(request.effectiveDate()).isEqualTo(effectiveDate);
        assertThat(request.terminationDate()).isEqualTo(terminationDate);
        assertThat(request.notes()).isEqualTo("notes");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.SECONDARY, "Aetna", "POL789",
                null, null, null, null, null, null, null);

        // Then
        assertThat(request.groupNumber()).isNull();
        assertThat(request.subscriberName()).isNull();
        assertThat(request.subscriberDob()).isNull();
        assertThat(request.relationshipToPatient()).isNull();
        assertThat(request.effectiveDate()).isNull();
        assertThat(request.terminationDate()).isNull();
        assertThat(request.notes()).isNull();
    }
}
