package com.nicusystem.clinical;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new clinical note.
 *
 * @param patientId          reference to the patient
 * @param noteType           type of clinical note
 * @param subjectiveFindings subjective findings (S in SOAP)
 * @param objectiveFindings  objective findings (O in SOAP)
 * @param assessment         assessment (A in SOAP)
 * @param plan               plan (P in SOAP)
 * @param freeText           free text for non-SOAP notes
 * @param authorId           author identifier
 * @param authorRole         author's role
 * @param recordedAt         when the note was recorded
 */
public record CreateClinicalNoteRequest(
        @NotNull UUID patientId,
        @NotNull NoteType noteType,
        String subjectiveFindings,
        String objectiveFindings,
        String assessment,
        String plan,
        String freeText,
        @NotNull String authorId,
        String authorRole,
        @NotNull Instant recordedAt
) {
}
