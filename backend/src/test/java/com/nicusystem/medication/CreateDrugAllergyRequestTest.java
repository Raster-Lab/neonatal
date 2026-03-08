package com.nicusystem.medication;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateDrugAllergyRequest record.
 */
class CreateDrugAllergyRequestTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreateDrugAllergyRequest request =
                new CreateDrugAllergyRequest(
                        patientId, "Penicillin", "Anaphylaxis",
                        AllergySeverity.LIFE_THREATENING,
                        "Confirmed by allergist");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.allergenName()).isEqualTo("Penicillin");
        assertThat(request.reactionType()).isEqualTo("Anaphylaxis");
        assertThat(request.severity())
                .isEqualTo(AllergySeverity.LIFE_THREATENING);
        assertThat(request.notes()).isEqualTo("Confirmed by allergist");
    }

    @Test
    void record_withNullOptionalFields_shouldStoreNull() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreateDrugAllergyRequest request =
                new CreateDrugAllergyRequest(
                        patientId, "Sulfa", "Hives",
                        AllergySeverity.MILD, null);

        // Then
        assertThat(request.notes()).isNull();
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateDrugAllergyRequest req1 =
                new CreateDrugAllergyRequest(
                        patientId, "Penicillin", "Rash",
                        AllergySeverity.MODERATE, null);
        final CreateDrugAllergyRequest req2 =
                new CreateDrugAllergyRequest(
                        patientId, "Penicillin", "Rash",
                        AllergySeverity.MODERATE, null);

        // When & Then
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final CreateDrugAllergyRequest request =
                new CreateDrugAllergyRequest(
                        UUID.randomUUID(), "Penicillin", "Rash",
                        AllergySeverity.MILD, null);

        // When & Then
        assertThat(request.toString())
                .contains("CreateDrugAllergyRequest");
    }
}
