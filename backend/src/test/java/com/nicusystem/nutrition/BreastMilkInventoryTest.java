package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BreastMilkInventory} entity setters and getters.
 */
class BreastMilkInventoryTest {

    @Test
    void setPatientId_shouldReturnSameValue() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();
        final UUID patientId = UUID.randomUUID();

        // When
        entity.setPatientId(patientId);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void setLabel_shouldReturnSameValue() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();

        // When
        entity.setLabel("BMK-001");

        // Then
        assertThat(entity.getLabel()).isEqualTo("BMK-001");
    }

    @Test
    void setVolumeMl_shouldReturnSameValue() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();

        // When
        entity.setVolumeMl(60.0);

        // Then
        assertThat(entity.getVolumeMl()).isEqualTo(60.0);
    }

    @Test
    void setCollectedAt_shouldReturnSameValue() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();
        final Instant now = Instant.now();

        // When
        entity.setCollectedAt(now);

        // Then
        assertThat(entity.getCollectedAt()).isEqualTo(now);
    }

    @Test
    void setExpiresAt_shouldReturnSameValue() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();
        final Instant expires = Instant.now();

        // When
        entity.setExpiresAt(expires);

        // Then
        assertThat(entity.getExpiresAt()).isEqualTo(expires);
    }

    @Test
    void setDonorMilk_true_isDonorMilkReturnsTrue() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();

        // When
        entity.setDonorMilk(true);

        // Then
        assertThat(entity.isDonorMilk()).isTrue();
    }

    @Test
    void setDonorMilk_false_isDonorMilkReturnsFalse() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();

        // When
        entity.setDonorMilk(false);

        // Then
        assertThat(entity.isDonorMilk()).isFalse();
    }

    @Test
    void setFortified_true_isFortifiedReturnsTrue() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();

        // When
        entity.setFortified(true);

        // Then
        assertThat(entity.isFortified()).isTrue();
    }

    @Test
    void setFortified_false_isFortifiedReturnsFalse() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();

        // When
        entity.setFortified(false);

        // Then
        assertThat(entity.isFortified()).isFalse();
    }

    @Test
    void setNotes_shouldReturnSameValue() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();

        // When
        entity.setNotes("Pasteurised");

        // Then
        assertThat(entity.getNotes()).isEqualTo("Pasteurised");
    }

    @Test
    void defaultBooleanValues_shouldBeFalse() {
        // Given / When
        final BreastMilkInventory entity = new BreastMilkInventory();

        // Then
        assertThat(entity.isDonorMilk()).isFalse();
        assertThat(entity.isFortified()).isFalse();
    }
}
