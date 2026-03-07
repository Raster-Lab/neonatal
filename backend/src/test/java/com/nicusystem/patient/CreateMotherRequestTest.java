package com.nicusystem.patient;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateMotherRequestTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given & When
        final CreateMotherRequest request = new CreateMotherRequest(
                "Jane", "Doe", "A", "positive",
                "Regular checkups", "Prenatal vitamins", "None");

        // Then
        assertThat(request.firstName()).isEqualTo("Jane");
        assertThat(request.lastName()).isEqualTo("Doe");
        assertThat(request.bloodType()).isEqualTo("A");
        assertThat(request.rhFactor()).isEqualTo("positive");
        assertThat(request.prenatalCare()).isEqualTo("Regular checkups");
        assertThat(request.medications()).isEqualTo("Prenatal vitamins");
        assertThat(request.infections()).isEqualTo("None");
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final CreateMotherRequest req1 = new CreateMotherRequest(
                "Jane", "Doe", null, null, null, null, null);
        final CreateMotherRequest req2 = new CreateMotherRequest(
                "Jane", "Doe", null, null, null, null, null);

        // When & Then
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final CreateMotherRequest request = new CreateMotherRequest(
                "Jane", "Doe", null, null, null, null, null);

        // When & Then
        assertThat(request.toString()).contains("CreateMotherRequest");
    }
}
