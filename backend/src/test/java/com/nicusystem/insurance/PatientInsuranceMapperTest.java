package com.nicusystem.insurance;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PatientInsuranceMapper.
 */
class PatientInsuranceMapperTest {

    private final PatientInsuranceMapper mapper = new PatientInsuranceMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final PatientInsurance entity = new PatientInsurance();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant subscriberDob = Instant.parse("1985-05-15T00:00:00Z");
        final Instant effectiveDate = Instant.now();
        final Instant terminationDate = Instant.now().plusSeconds(86400);
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setInsuranceType(InsuranceType.PRIMARY);
        entity.setInsurerName("Blue Cross");
        entity.setPolicyNumber("POL123");
        entity.setGroupNumber("GRP456");
        entity.setSubscriberName("John Doe");
        entity.setSubscriberDob(subscriberDob);
        entity.setRelationshipToPatient("Father");
        entity.setEffectiveDate(effectiveDate);
        entity.setTerminationDate(terminationDate);
        entity.setNotes("test note");
        entity.setActive(true);

        // When
        final PatientInsuranceDto dto = mapper.toDto(entity);

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
        assertThat(dto.notes()).isEqualTo("test note");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant subscriberDob = Instant.parse("1985-05-15T00:00:00Z");
        final Instant effectiveDate = Instant.now();
        final Instant terminationDate = Instant.now().plusSeconds(86400);
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.SECONDARY, "Aetna", "POL789",
                "GRP111", "Jane Doe", subscriberDob, "Mother",
                effectiveDate, terminationDate, "notes");

        // When
        final PatientInsurance entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getInsuranceType()).isEqualTo(InsuranceType.SECONDARY);
        assertThat(entity.getInsurerName()).isEqualTo("Aetna");
        assertThat(entity.getPolicyNumber()).isEqualTo("POL789");
        assertThat(entity.getGroupNumber()).isEqualTo("GRP111");
        assertThat(entity.getSubscriberName()).isEqualTo("Jane Doe");
        assertThat(entity.getSubscriberDob()).isEqualTo(subscriberDob);
        assertThat(entity.getRelationshipToPatient()).isEqualTo("Mother");
        assertThat(entity.getEffectiveDate()).isEqualTo(effectiveDate);
        assertThat(entity.getTerminationDate()).isEqualTo(terminationDate);
        assertThat(entity.getNotes()).isEqualTo("notes");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                null, null, null, null, null, null, null);

        // When
        final PatientInsurance entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getGroupNumber()).isNull();
        assertThat(entity.getSubscriberName()).isNull();
        assertThat(entity.getSubscriberDob()).isNull();
        assertThat(entity.getRelationshipToPatient()).isNull();
        assertThat(entity.getEffectiveDate()).isNull();
        assertThat(entity.getTerminationDate()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
