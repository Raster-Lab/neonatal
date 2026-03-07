package com.nicusystem.patient;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenderTest {

    @Test
    void values_shouldContainAllGenders() {
        // Given & When
        final Gender[] values = Gender.values();

        // Then
        assertThat(values).containsExactly(Gender.MALE, Gender.FEMALE, Gender.UNKNOWN);
    }

    @Test
    void valueOf_validName_shouldReturnEnum() {
        assertThat(Gender.valueOf("MALE")).isEqualTo(Gender.MALE);
        assertThat(Gender.valueOf("FEMALE")).isEqualTo(Gender.FEMALE);
        assertThat(Gender.valueOf("UNKNOWN")).isEqualTo(Gender.UNKNOWN);
    }
}
