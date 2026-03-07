package com.nicusystem.clinical;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between ClinicalNote entities and DTOs.
 */
@Component
public class ClinicalNoteMapper {

    /**
     * Converts a ClinicalNote entity to a ClinicalNoteDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public ClinicalNoteDto toDto(final ClinicalNote entity) {
        return new ClinicalNoteDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getNoteType(),
                entity.getSubjectiveFindings(),
                entity.getObjectiveFindings(),
                entity.getAssessment(),
                entity.getPlan(),
                entity.getFreeText(),
                entity.getAuthorId(),
                entity.getAuthorRole(),
                entity.getCoSignerId(),
                entity.getCoSignedAt(),
                entity.getRecordedAt(),
                entity.isActive()
        );
    }

    /**
     * Converts a CreateClinicalNoteRequest to a ClinicalNote entity.
     *
     * @param request the creation request
     * @return the ClinicalNote entity
     */
    public ClinicalNote toEntity(final CreateClinicalNoteRequest request) {
        final ClinicalNote note = new ClinicalNote();
        note.setPatientId(request.patientId());
        note.setNoteType(request.noteType());
        note.setSubjectiveFindings(request.subjectiveFindings());
        note.setObjectiveFindings(request.objectiveFindings());
        note.setAssessment(request.assessment());
        note.setPlan(request.plan());
        note.setFreeText(request.freeText());
        note.setAuthorId(request.authorId());
        note.setAuthorRole(request.authorRole());
        note.setRecordedAt(request.recordedAt());
        note.setActive(true);
        return note;
    }
}
