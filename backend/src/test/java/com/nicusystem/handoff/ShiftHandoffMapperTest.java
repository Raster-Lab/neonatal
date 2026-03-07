package com.nicusystem.handoff;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ShiftHandoffMapper.
 */
class ShiftHandoffMapperTest {

    private final ShiftHandoffMapper mapper = new ShiftHandoffMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final ShiftHandoff entity = new ShiftHandoff();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setHandoffFormat(HandoffFormat.IPASS);
        entity.setHandoffAt(handoffAt);
        entity.setHandingOffProvider("Dr. Smith");
        entity.setReceivingProvider("Dr. Jones");
        entity.setIpassIllnessSeverity("stable");
        entity.setIpassPatientSummary("28-week premature infant");
        entity.setIpassActionList("Check morning labs");
        entity.setIpassSituationAwareness("If O2 drops, increase FiO2");
        entity.setIpassSynthesisByReceiver("Understood");
        entity.setSbarSituation(null);
        entity.setSbarBackground(null);
        entity.setSbarAssessment(null);
        entity.setSbarRecommendation(null);
        entity.setNotes("Routine handoff");
        entity.setActive(true);

        // When
        final ShiftHandoffDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.handoffFormat()).isEqualTo(HandoffFormat.IPASS);
        assertThat(dto.handoffAt()).isEqualTo(handoffAt);
        assertThat(dto.handingOffProvider()).isEqualTo("Dr. Smith");
        assertThat(dto.receivingProvider()).isEqualTo("Dr. Jones");
        assertThat(dto.ipassIllnessSeverity()).isEqualTo("stable");
        assertThat(dto.ipassPatientSummary()).isEqualTo("28-week premature infant");
        assertThat(dto.ipassActionList()).isEqualTo("Check morning labs");
        assertThat(dto.ipassSituationAwareness()).isEqualTo("If O2 drops, increase FiO2");
        assertThat(dto.ipassSynthesisByReceiver()).isEqualTo("Understood");
        assertThat(dto.sbarSituation()).isNull();
        assertThat(dto.sbarBackground()).isNull();
        assertThat(dto.sbarAssessment()).isNull();
        assertThat(dto.sbarRecommendation()).isNull();
        assertThat(dto.notes()).isEqualTo("Routine handoff");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();
        final CreateShiftHandoffRequest request = new CreateShiftHandoffRequest(
                patientId, HandoffFormat.SBAR, handoffAt,
                "Nurse A", "Nurse B",
                null, null, null, null, null,
                "Patient desaturating", "DOL 5, ex-28 weeker",
                "Likely atelectasis", "Consider CPAP trial",
                "Urgent SBAR");

        // When
        final ShiftHandoff entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getHandoffFormat()).isEqualTo(HandoffFormat.SBAR);
        assertThat(entity.getHandoffAt()).isEqualTo(handoffAt);
        assertThat(entity.getHandingOffProvider()).isEqualTo("Nurse A");
        assertThat(entity.getReceivingProvider()).isEqualTo("Nurse B");
        assertThat(entity.getIpassIllnessSeverity()).isNull();
        assertThat(entity.getIpassPatientSummary()).isNull();
        assertThat(entity.getSbarSituation()).isEqualTo("Patient desaturating");
        assertThat(entity.getSbarBackground()).isEqualTo("DOL 5, ex-28 weeker");
        assertThat(entity.getSbarAssessment()).isEqualTo("Likely atelectasis");
        assertThat(entity.getSbarRecommendation()).isEqualTo("Consider CPAP trial");
        assertThat(entity.getNotes()).isEqualTo("Urgent SBAR");
        assertThat(entity.isActive()).isTrue();
    }

    @Test
    void toEntity_shouldSetActiveTrue() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();
        final CreateShiftHandoffRequest request = new CreateShiftHandoffRequest(
                patientId, HandoffFormat.IPASS, handoffAt,
                "Dr. X", "Dr. Y",
                null, null, null, null, null,
                null, null, null, null,
                null);

        // When
        final ShiftHandoff entity = mapper.toEntity(request);

        // Then
        assertThat(entity.isActive()).isTrue();
    }
}
