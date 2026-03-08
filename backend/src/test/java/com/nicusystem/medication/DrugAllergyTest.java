package com.nicusystem.medication;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DrugAllergy entity.
 */
class DrugAllergyTest {

    @Test
    void noArgConstructor_shouldCreateInstance() {
        // Given & When
        final DrugAllergy allergy = new DrugAllergy();

        // Then
        assertThat(allergy).isNotNull();
        assertThat(allergy.isActive()).isTrue();
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // Given
        final DrugAllergy allergy = new DrugAllergy();
        final UUID patientId = UUID.randomUUID();

        // When
        allergy.setPatientId(patientId);
        allergy.setAllergenName("Penicillin");
        allergy.setReactionType("Anaphylaxis");
        allergy.setSeverity(AllergySeverity.SEVERE);
        allergy.setNotes("Confirmed by allergist");
        allergy.setActive(false);

        // Then
        assertThat(allergy.getPatientId()).isEqualTo(patientId);
        assertThat(allergy.getAllergenName()).isEqualTo("Penicillin");
        assertThat(allergy.getReactionType()).isEqualTo("Anaphylaxis");
        assertThat(allergy.getSeverity()).isEqualTo(AllergySeverity.SEVERE);
        assertThat(allergy.getNotes()).isEqualTo("Confirmed by allergist");
        assertThat(allergy.isActive()).isFalse();
    }

    @Test
    void settersAndGetters_nullOptionalFields_shouldAcceptNull() {
        // Given
        final DrugAllergy allergy = new DrugAllergy();

        // When
        allergy.setNotes(null);

        // Then
        assertThat(allergy.getNotes()).isNull();
    }
}
