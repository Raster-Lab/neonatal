package com.nicusystem.patient;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MotherTest {

    @Test
    void noArgConstructor_shouldCreateInstanceWithActiveTrue() {
        // Given & When
        final Mother mother = new Mother();

        // Then
        assertThat(mother.isActive()).isTrue();
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // Given
        final Mother mother = new Mother();

        // When
        mother.setFirstName("Jane");
        mother.setLastName("Doe");
        mother.setBloodType("A");
        mother.setRhFactor("positive");
        mother.setPrenatalCare("Regular checkups");
        mother.setMedications("Prenatal vitamins");
        mother.setInfections("None");
        mother.setActive(false);

        // Then
        assertThat(mother.getFirstName()).isEqualTo("Jane");
        assertThat(mother.getLastName()).isEqualTo("Doe");
        assertThat(mother.getBloodType()).isEqualTo("A");
        assertThat(mother.getRhFactor()).isEqualTo("positive");
        assertThat(mother.getPrenatalCare()).isEqualTo("Regular checkups");
        assertThat(mother.getMedications()).isEqualTo("Prenatal vitamins");
        assertThat(mother.getInfections()).isEqualTo("None");
        assertThat(mother.isActive()).isFalse();
    }
}
