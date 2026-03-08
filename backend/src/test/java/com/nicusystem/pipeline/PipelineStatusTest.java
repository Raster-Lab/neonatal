package com.nicusystem.pipeline;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PipelineStatus enum.
 */
class PipelineStatusTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final PipelineStatus[] values = PipelineStatus.values();

        // Then
        assertThat(values).containsExactly(
                PipelineStatus.ACTIVE,
                PipelineStatus.PAUSED,
                PipelineStatus.ERROR,
                PipelineStatus.DISCONNECTED
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(PipelineStatus.valueOf("ACTIVE")).isEqualTo(PipelineStatus.ACTIVE);
        assertThat(PipelineStatus.valueOf("PAUSED")).isEqualTo(PipelineStatus.PAUSED);
        assertThat(PipelineStatus.valueOf("ERROR")).isEqualTo(PipelineStatus.ERROR);
        assertThat(PipelineStatus.valueOf("DISCONNECTED")).isEqualTo(PipelineStatus.DISCONNECTED);
    }
}
