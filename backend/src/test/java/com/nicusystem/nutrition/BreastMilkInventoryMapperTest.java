package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BreastMilkInventoryMapper}.
 */
class BreastMilkInventoryMapperTest {

    private final BreastMilkInventoryMapper mapper = new BreastMilkInventoryMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        final Instant expires = Instant.now();
        entity.setPatientId(patientId);
        entity.setLabel("BMK-001");
        entity.setVolumeMl(60.0);
        entity.setCollectedAt(now);
        entity.setExpiresAt(expires);
        entity.setDonorMilk(true);
        entity.setFortified(false);
        entity.setNotes("Test");

        // When
        final BreastMilkInventoryDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.label()).isEqualTo("BMK-001");
        assertThat(dto.volumeMl()).isEqualTo(60.0);
        assertThat(dto.collectedAt()).isEqualTo(now);
        assertThat(dto.expiresAt()).isEqualTo(expires);
        assertThat(dto.donorMilk()).isTrue();
        assertThat(dto.fortified()).isFalse();
        assertThat(dto.notes()).isEqualTo("Test");
    }

    @Test
    void toDto_donorMilkFalse_shouldMapCorrectly() {
        // Given
        final BreastMilkInventory entity = new BreastMilkInventory();
        entity.setPatientId(UUID.randomUUID());
        entity.setLabel("OWN-001");
        entity.setVolumeMl(30.0);
        entity.setCollectedAt(Instant.now());
        entity.setDonorMilk(false);
        entity.setFortified(true);

        // When
        final BreastMilkInventoryDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.donorMilk()).isFalse();
        assertThat(dto.fortified()).isTrue();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant collectedAt = Instant.now();
        final Instant expiresAt = Instant.now();
        final CreateBreastMilkInventoryRequest request = new CreateBreastMilkInventoryRequest(
                patientId, "BMK-002", 45.0, collectedAt, expiresAt, true, true, "Mapper test");

        // When
        final BreastMilkInventory entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getLabel()).isEqualTo("BMK-002");
        assertThat(entity.getVolumeMl()).isEqualTo(45.0);
        assertThat(entity.getCollectedAt()).isEqualTo(collectedAt);
        assertThat(entity.getExpiresAt()).isEqualTo(expiresAt);
        assertThat(entity.isDonorMilk()).isTrue();
        assertThat(entity.isFortified()).isTrue();
        assertThat(entity.getNotes()).isEqualTo("Mapper test");
    }

    @Test
    void toEntity_withFalseFlags_shouldMapCorrectly() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateBreastMilkInventoryRequest request = new CreateBreastMilkInventoryRequest(
                patientId, "OWN-002", 20.0, Instant.now(), null, false, false, null);

        // When
        final BreastMilkInventory entity = mapper.toEntity(request);

        // Then
        assertThat(entity.isDonorMilk()).isFalse();
        assertThat(entity.isFortified()).isFalse();
        assertThat(entity.getExpiresAt()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
