package com.nicusystem.respiratory;

import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link ApneaEvent} entities and DTOs.
 */
@Component
public class ApneaEventMapper {

    /**
     * Converts an {@link ApneaEvent} entity to an {@link ApneaEventDto}.
     *
     * @param entity the apnea event entity
     * @return the corresponding DTO
     */
    public ApneaEventDto toDto(final ApneaEvent entity) {
        return new ApneaEventDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getOccurredAt(),
                entity.getDurationSeconds(),
                entity.isAssociatedBradycardia(),
                entity.getLowestHeartRate(),
                entity.getLowestSpo2(),
                entity.isStimulationRequired(),
                entity.isBaggingRequired(),
                entity.getCaffeineDose(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a {@link CreateApneaEventRequest} to a new {@link ApneaEvent} entity.
     *
     * @param request the creation request
     * @return a new, unpersisted apnea event entity
     */
    public ApneaEvent toEntity(final CreateApneaEventRequest request) {
        final ApneaEvent entity = new ApneaEvent();
        entity.setPatientId(request.patientId());
        entity.setOccurredAt(request.occurredAt());
        entity.setDurationSeconds(request.durationSeconds());
        entity.setAssociatedBradycardia(request.associatedBradycardia());
        entity.setLowestHeartRate(request.lowestHeartRate());
        entity.setLowestSpo2(request.lowestSpo2());
        entity.setStimulationRequired(request.stimulationRequired());
        entity.setBaggingRequired(request.baggingRequired());
        entity.setCaffeineDose(request.caffeineDose());
        entity.setNotes(request.notes());
        return entity;
    }
}
