package com.nicusystem.clinical;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for NoteType enum.
 */
class NoteTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final NoteType[] values = NoteType.values();

        // Then
        assertThat(values).containsExactly(
                NoteType.PROGRESS,
                NoteType.ADMISSION,
                NoteType.PROCEDURE,
                NoteType.DISCHARGE,
                NoteType.CONSULTATION
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(NoteType.valueOf("PROGRESS")).isEqualTo(NoteType.PROGRESS);
        assertThat(NoteType.valueOf("ADMISSION")).isEqualTo(NoteType.ADMISSION);
        assertThat(NoteType.valueOf("PROCEDURE")).isEqualTo(NoteType.PROCEDURE);
        assertThat(NoteType.valueOf("DISCHARGE")).isEqualTo(NoteType.DISCHARGE);
        assertThat(NoteType.valueOf("CONSULTATION")).isEqualTo(NoteType.CONSULTATION);
    }
}
