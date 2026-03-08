package com.nicusystem.rounding;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between DailyRoundingSummary entities and DTOs.
 */
@Component
public class DailyRoundingSummaryMapper {

    /**
     * Converts a DailyRoundingSummary entity to a DailyRoundingSummaryDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public DailyRoundingSummaryDto toDto(final DailyRoundingSummary entity) {
        return new DailyRoundingSummaryDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getRoundingDate(),
                entity.getPresentingProblems(),
                entity.getOvernightEvents(),
                entity.getCurrentVitalsSummary(),
                entity.getCurrentMedicationsSummary(),
                entity.getAssessmentPlan(),
                entity.getAttendingPhysician(),
                entity.getParticipants(),
                entity.getActionItems(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateDailyRoundingSummaryRequest to a DailyRoundingSummary entity.
     *
     * @param request the creation request
     * @return the DailyRoundingSummary entity
     */
    public DailyRoundingSummary toEntity(
            final CreateDailyRoundingSummaryRequest request) {
        final DailyRoundingSummary summary = new DailyRoundingSummary();
        summary.setPatientId(request.patientId());
        summary.setRoundingDate(request.roundingDate());
        summary.setPresentingProblems(request.presentingProblems());
        summary.setOvernightEvents(request.overnightEvents());
        summary.setCurrentVitalsSummary(request.currentVitalsSummary());
        summary.setCurrentMedicationsSummary(
                request.currentMedicationsSummary());
        summary.setAssessmentPlan(request.assessmentPlan());
        summary.setAttendingPhysician(request.attendingPhysician());
        summary.setParticipants(request.participants());
        summary.setActionItems(request.actionItems());
        summary.setNotes(request.notes());
        return summary;
    }
}
