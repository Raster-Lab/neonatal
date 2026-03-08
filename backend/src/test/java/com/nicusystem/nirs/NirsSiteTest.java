package com.nicusystem.nirs;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for NirsSite enum.
 */
class NirsSiteTest {

    @Test
    void shouldContainAllExpectedValues() {
        // When
        final NirsSite[] values = NirsSite.values();

        // Then
        assertThat(values).containsExactlyInAnyOrder(
                NirsSite.LEFT_CEREBRAL,
                NirsSite.RIGHT_CEREBRAL,
                NirsSite.SOMATIC,
                NirsSite.RENAL,
                NirsSite.MESENTERIC
        );
    }

    @Test
    void shouldResolveFromName() {
        // When / Then
        assertThat(NirsSite.valueOf("LEFT_CEREBRAL")).isEqualTo(NirsSite.LEFT_CEREBRAL);
        assertThat(NirsSite.valueOf("RENAL")).isEqualTo(NirsSite.RENAL);
        assertThat(NirsSite.valueOf("MESENTERIC")).isEqualTo(NirsSite.MESENTERIC);
    }
}
