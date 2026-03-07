package com.nicusystem.insurance;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PatientInsurance entity.
 */
class PatientInsuranceTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();
        final UUID patientId = UUID.randomUUID();

        // When
        insurance.setPatientId(patientId);

        // Then
        assertThat(insurance.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetInsuranceType() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();

        // When
        insurance.setInsuranceType(InsuranceType.PRIMARY);

        // Then
        assertThat(insurance.getInsuranceType()).isEqualTo(InsuranceType.PRIMARY);
    }

    @Test
    void shouldSetAndGetInsurerName() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();

        // When
        insurance.setInsurerName("Blue Cross Blue Shield");

        // Then
        assertThat(insurance.getInsurerName()).isEqualTo("Blue Cross Blue Shield");
    }

    @Test
    void shouldSetAndGetPolicyNumber() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();

        // When
        insurance.setPolicyNumber("POL123456");

        // Then
        assertThat(insurance.getPolicyNumber()).isEqualTo("POL123456");
    }

    @Test
    void shouldSetAndGetGroupNumber() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();

        // When
        insurance.setGroupNumber("GRP789");

        // Then
        assertThat(insurance.getGroupNumber()).isEqualTo("GRP789");
    }

    @Test
    void shouldSetAndGetSubscriberName() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();

        // When
        insurance.setSubscriberName("John Doe");

        // Then
        assertThat(insurance.getSubscriberName()).isEqualTo("John Doe");
    }

    @Test
    void shouldSetAndGetSubscriberDob() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();
        final Instant dob = Instant.parse("1985-05-15T00:00:00Z");

        // When
        insurance.setSubscriberDob(dob);

        // Then
        assertThat(insurance.getSubscriberDob()).isEqualTo(dob);
    }

    @Test
    void shouldSetAndGetRelationshipToPatient() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();

        // When
        insurance.setRelationshipToPatient("Father");

        // Then
        assertThat(insurance.getRelationshipToPatient()).isEqualTo("Father");
    }

    @Test
    void shouldSetAndGetEffectiveDate() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();
        final Instant effectiveDate = Instant.now();

        // When
        insurance.setEffectiveDate(effectiveDate);

        // Then
        assertThat(insurance.getEffectiveDate()).isEqualTo(effectiveDate);
    }

    @Test
    void shouldSetAndGetTerminationDate() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();
        final Instant terminationDate = Instant.now().plusSeconds(86400);

        // When
        insurance.setTerminationDate(terminationDate);

        // Then
        assertThat(insurance.getTerminationDate()).isEqualTo(terminationDate);
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();

        // When
        insurance.setNotes("HMO plan");

        // Then
        assertThat(insurance.getNotes()).isEqualTo("HMO plan");
    }

    @Test
    void shouldSetAndGetActive() {
        // Given
        final PatientInsurance insurance = new PatientInsurance();

        // When
        insurance.setActive(false);

        // Then
        assertThat(insurance.isActive()).isFalse();
    }

    @Test
    void shouldDefaultActiveTrueOnConstruction() {
        // Given / When
        final PatientInsurance insurance = new PatientInsurance();

        // Then
        assertThat(insurance.isActive()).isTrue();
    }
}
