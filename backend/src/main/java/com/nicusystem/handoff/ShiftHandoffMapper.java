package com.nicusystem.handoff;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between ShiftHandoff entities and DTOs.
 */
@Component
public class ShiftHandoffMapper {

    /**
     * Converts a ShiftHandoff entity to a ShiftHandoffDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public ShiftHandoffDto toDto(final ShiftHandoff entity) {
        return new ShiftHandoffDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getHandoffFormat(),
                entity.getHandoffAt(),
                entity.getHandingOffProvider(),
                entity.getReceivingProvider(),
                entity.getIpassIllnessSeverity(),
                entity.getIpassPatientSummary(),
                entity.getIpassActionList(),
                entity.getIpassSituationAwareness(),
                entity.getIpassSynthesisByReceiver(),
                entity.getSbarSituation(),
                entity.getSbarBackground(),
                entity.getSbarAssessment(),
                entity.getSbarRecommendation(),
                entity.getNotes(),
                entity.isActive()
        );
    }

    /**
     * Converts a CreateShiftHandoffRequest to a ShiftHandoff entity.
     *
     * @param request the creation request
     * @return the ShiftHandoff entity
     */
    public ShiftHandoff toEntity(final CreateShiftHandoffRequest request) {
        final ShiftHandoff handoff = new ShiftHandoff();
        handoff.setPatientId(request.patientId());
        handoff.setHandoffFormat(request.handoffFormat());
        handoff.setHandoffAt(request.handoffAt());
        handoff.setHandingOffProvider(request.handingOffProvider());
        handoff.setReceivingProvider(request.receivingProvider());
        handoff.setIpassIllnessSeverity(request.ipassIllnessSeverity());
        handoff.setIpassPatientSummary(request.ipassPatientSummary());
        handoff.setIpassActionList(request.ipassActionList());
        handoff.setIpassSituationAwareness(request.ipassSituationAwareness());
        handoff.setIpassSynthesisByReceiver(request.ipassSynthesisByReceiver());
        handoff.setSbarSituation(request.sbarSituation());
        handoff.setSbarBackground(request.sbarBackground());
        handoff.setSbarAssessment(request.sbarAssessment());
        handoff.setSbarRecommendation(request.sbarRecommendation());
        handoff.setNotes(request.notes());
        handoff.setActive(true);
        return handoff;
    }
}
