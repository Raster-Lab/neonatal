package com.nicusystem.medication;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DrugAllergyMapper.
 */
class DrugAllergyMapperTest {

    private final DrugAllergyMapper mapper = new DrugAllergyMapper();

    @Test
    void toDto_mapsAllFields() {
        // Given
        final DrugAllergy entity = new DrugAllergy();
        entity.setPatientId(UUID.randomUUID());
        entity.setAllergenName("Penicillin");
        entity.setReactionType("Anaphylaxis");
        entity.setSeverity(AllergySeverity.SEVERE);
        entity.setNotes("Confirmed by allergist");
        entity.setActive(true);

        // When
        final DrugAllergyDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(entity.getId());
        assertThat(dto.patientId()).isEqualTo(entity.getPatientId());
        assertThat(dto.allergenName()).isEqualTo("Penicillin");
        assertThat(dto.reactionType()).isEqualTo("Anaphylaxis");
        assertThat(dto.severity()).isEqualTo(AllergySeverity.SEVERE);
        assertThat(dto.notes()).isEqualTo("Confirmed by allergist");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void toEntity_mapsAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateDrugAllergyRequest request =
                new CreateDrugAllergyRequest(
                        patientId, "Sulfa", "Hives",
                        AllergySeverity.MODERATE, "Mild reaction");

        // When
        final DrugAllergy entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getAllergenName()).isEqualTo("Sulfa");
        assertThat(entity.getReactionType()).isEqualTo("Hives");
        assertThat(entity.getSeverity())
                .isEqualTo(AllergySeverity.MODERATE);
        assertThat(entity.getNotes()).isEqualTo("Mild reaction");
        assertThat(entity.isActive()).isTrue();
    }
}
