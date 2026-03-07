package com.nicusystem.common;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for BaseEntity.
 */
class BaseEntityTest {

    @Test
    void shouldSetAndGetId() {
        // Given
        final TestEntity entity = new TestEntity();
        final UUID id = UUID.randomUUID();

        // When
        entity.setId(id);

        // Then
        assertThat(entity.getId()).isEqualTo(id);
    }

    @Test
    void shouldSetAndGetCreatedAt() {
        // Given
        final TestEntity entity = new TestEntity();
        final Instant now = Instant.now();

        // When
        entity.setCreatedAt(now);

        // Then
        assertThat(entity.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetUpdatedAt() {
        // Given
        final TestEntity entity = new TestEntity();
        final Instant now = Instant.now();

        // When
        entity.setUpdatedAt(now);

        // Then
        assertThat(entity.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetCreatedBy() {
        // Given
        final TestEntity entity = new TestEntity();

        // When
        entity.setCreatedBy("physician");

        // Then
        assertThat(entity.getCreatedBy()).isEqualTo("physician");
    }

    @Test
    void shouldSetAndGetUpdatedBy() {
        // Given
        final TestEntity entity = new TestEntity();

        // When
        entity.setUpdatedBy("nurse");

        // Then
        assertThat(entity.getUpdatedBy()).isEqualTo("nurse");
    }

    @Test
    void shouldSetAndGetVersion() {
        // Given
        final TestEntity entity = new TestEntity();

        // When
        entity.setVersion(1L);

        // Then
        assertThat(entity.getVersion()).isEqualTo(1L);
    }

    /**
     * Concrete subclass for testing the abstract BaseEntity.
     */
    private static class TestEntity extends BaseEntity {
    }
}
