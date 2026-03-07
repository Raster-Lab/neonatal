package com.nicusystem.clinical;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for ClinicalNote entity.
 *
 * @param id                  unique identifier
 * @param patientId           reference to the patient
 * @param noteType            type of clinical note
 * @param subjectiveFindings  subjective findings (S in SOAP)
 * @param objectiveFindings   objective findings (O in SOAP)
 * @param assessment          assessment (A in SOAP)
 * @param plan                plan (P in SOAP)
 * @param freeText            free text for non-SOAP notes
 * @param authorId            author identifier
 * @param authorRole          author's role
 * @param coSignerId          co-signer identifier
 * @param coSignedAt          when co-signature was provided
 * @param recordedAt          when the note was recorded
 * @param active              whether the note is active
 */
public record ClinicalNoteDto(
        UUID id,
        UUID patientId,
        NoteType noteType,
        String subjectiveFindings,
        String objectiveFindings,
        String assessment,
        String plan,
        String freeText,
        String authorId,
        String authorRole,
        String coSignerId,
        Instant coSignedAt,
        Instant recordedAt,
        boolean active
) {
}
