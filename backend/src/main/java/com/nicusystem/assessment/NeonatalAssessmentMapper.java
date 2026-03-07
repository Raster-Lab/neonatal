package com.nicusystem.assessment;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between NeonatalAssessment entities and DTOs.
 */
@Component
public class NeonatalAssessmentMapper {

    /**
     * Converts a NeonatalAssessment entity to a NeonatalAssessmentDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public NeonatalAssessmentDto toDto(final NeonatalAssessment entity) {
        return new NeonatalAssessmentDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getAssessmentType(),
                entity.getAssessedAt(),
                entity.getAssessedBy(),
                entity.getNeuroTone(),
                entity.getNeuroReflexes(),
                entity.getNeuroSeizureActivity(),
                entity.getNeuroFontanelleStatus(),
                entity.getCardiacPerfusion(),
                entity.getCardiacCapillaryRefillSeconds(),
                entity.getCardiacHeartSounds(),
                entity.getCardiacPulses(),
                entity.getRespBreathSounds(),
                entity.getRespWorkOfBreathing(),
                entity.getRespChestMovement(),
                entity.getGiAbdomen(),
                entity.getGiBowelSounds(),
                entity.getGiStoolPattern(),
                entity.getGiFeedingTolerance(),
                entity.getGuUrineOutputMlPerKgHr(),
                entity.getGuGenitaliaAssessment(),
                entity.getMskelExtremities(),
                entity.getMskelHips(),
                entity.getMskelSpine(),
                entity.getIntegSkinIntegrity(),
                entity.getIntegSkinColor(),
                entity.getIntegRashes(),
                entity.getIntegJaundice(),
                entity.getIntegBradenQScore(),
                entity.getNotes()
        );
    }

    /**
     * Converts a CreateNeonatalAssessmentRequest to a NeonatalAssessment entity.
     *
     * @param request the creation request
     * @return the NeonatalAssessment entity
     */
    public NeonatalAssessment toEntity(final CreateNeonatalAssessmentRequest request) {
        final NeonatalAssessment assessment = new NeonatalAssessment();
        assessment.setPatientId(request.patientId());
        assessment.setAssessmentType(request.assessmentType());
        assessment.setAssessedAt(request.assessedAt());
        assessment.setAssessedBy(request.assessedBy());
        assessment.setNeuroTone(request.neuroTone());
        assessment.setNeuroReflexes(request.neuroReflexes());
        assessment.setNeuroSeizureActivity(request.neuroSeizureActivity());
        assessment.setNeuroFontanelleStatus(request.neuroFontanelleStatus());
        assessment.setCardiacPerfusion(request.cardiacPerfusion());
        assessment.setCardiacCapillaryRefillSeconds(request.cardiacCapillaryRefillSeconds());
        assessment.setCardiacHeartSounds(request.cardiacHeartSounds());
        assessment.setCardiacPulses(request.cardiacPulses());
        assessment.setRespBreathSounds(request.respBreathSounds());
        assessment.setRespWorkOfBreathing(request.respWorkOfBreathing());
        assessment.setRespChestMovement(request.respChestMovement());
        assessment.setGiAbdomen(request.giAbdomen());
        assessment.setGiBowelSounds(request.giBowelSounds());
        assessment.setGiStoolPattern(request.giStoolPattern());
        assessment.setGiFeedingTolerance(request.giFeedingTolerance());
        assessment.setGuUrineOutputMlPerKgHr(request.guUrineOutputMlPerKgHr());
        assessment.setGuGenitaliaAssessment(request.guGenitaliaAssessment());
        assessment.setMskelExtremities(request.mskelExtremities());
        assessment.setMskelHips(request.mskelHips());
        assessment.setMskelSpine(request.mskelSpine());
        assessment.setIntegSkinIntegrity(request.integSkinIntegrity());
        assessment.setIntegSkinColor(request.integSkinColor());
        assessment.setIntegRashes(request.integRashes());
        assessment.setIntegJaundice(request.integJaundice());
        assessment.setIntegBradenQScore(request.integBradenQScore());
        assessment.setNotes(request.notes());
        return assessment;
    }
}
