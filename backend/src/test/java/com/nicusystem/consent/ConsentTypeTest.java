package com.nicusystem.consent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ConsentType enum.
 */
class ConsentTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final ConsentType[] values = ConsentType.values();

        // Then
        assertThat(values).containsExactly(
                ConsentType.TREATMENT,
                ConsentType.PHOTOGRAPHY,
                ConsentType.RESEARCH_PARTICIPATION,
                ConsentType.SURGERY,
                ConsentType.BLOOD_TRANSFUSION,
                ConsentType.AUTOPSY
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(ConsentType.valueOf("TREATMENT")).isEqualTo(ConsentType.TREATMENT);
        assertThat(ConsentType.valueOf("PHOTOGRAPHY")).isEqualTo(ConsentType.PHOTOGRAPHY);
        assertThat(ConsentType.valueOf("RESEARCH_PARTICIPATION"))
                .isEqualTo(ConsentType.RESEARCH_PARTICIPATION);
        assertThat(ConsentType.valueOf("SURGERY")).isEqualTo(ConsentType.SURGERY);
        assertThat(ConsentType.valueOf("BLOOD_TRANSFUSION"))
                .isEqualTo(ConsentType.BLOOD_TRANSFUSION);
        assertThat(ConsentType.valueOf("AUTOPSY")).isEqualTo(ConsentType.AUTOPSY);
    }
}
