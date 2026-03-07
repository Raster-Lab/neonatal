package com.nicusystem.medication;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DrugInteractionMapper.
 */
class DrugInteractionMapperTest {

    private final DrugInteractionMapper mapper = new DrugInteractionMapper();

    @Test
    void toDto_mapsAllFields() {
        // Given
        final DrugInteraction entity = new DrugInteraction();
        entity.setDrug1Name("Warfarin");
        entity.setDrug2Name("Aspirin");
        entity.setInteractionSeverity(DrugInteractionSeverity.CONTRAINDICATED);
        entity.setDescription("Increased bleeding risk");
        entity.setClinicalEffect("Hemorrhage");
        entity.setManagement("Avoid combination");
        entity.setActive(true);

        // When
        final DrugInteractionDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.drug1Name()).isEqualTo("Warfarin");
        assertThat(dto.drug2Name()).isEqualTo("Aspirin");
        assertThat(dto.interactionSeverity()).isEqualTo(DrugInteractionSeverity.CONTRAINDICATED);
        assertThat(dto.description()).isEqualTo("Increased bleeding risk");
        assertThat(dto.clinicalEffect()).isEqualTo("Hemorrhage");
        assertThat(dto.management()).isEqualTo("Avoid combination");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void toEntity_mapsAllFields() {
        // Given
        final CreateDrugInteractionRequest request = new CreateDrugInteractionRequest(
                "Warfarin", "Aspirin", DrugInteractionSeverity.MAJOR,
                "Bleeding risk", "Hemorrhage", "Monitor INR");

        // When
        final DrugInteraction entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getDrug1Name()).isEqualTo("Warfarin");
        assertThat(entity.getDrug2Name()).isEqualTo("Aspirin");
        assertThat(entity.getInteractionSeverity()).isEqualTo(DrugInteractionSeverity.MAJOR);
        assertThat(entity.getDescription()).isEqualTo("Bleeding risk");
        assertThat(entity.getClinicalEffect()).isEqualTo("Hemorrhage");
        assertThat(entity.getManagement()).isEqualTo("Monitor INR");
        assertThat(entity.isActive()).isTrue();
    }
}
