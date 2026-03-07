package com.nicusystem.clinical;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ClinicalNote entities.
 */
@Repository
public interface ClinicalNoteRepository extends JpaRepository<ClinicalNote, UUID> {

    /**
     * Finds clinical notes for a patient ordered by recorded time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of clinical notes
     */
    Page<ClinicalNote> findByPatientIdOrderByRecordedAtDesc(
            UUID patientId, Pageable pageable);

    /**
     * Finds clinical notes for a patient filtered by note type, ordered descending.
     *
     * @param patientId the patient UUID
     * @param noteType  the note type
     * @param pageable  pagination information
     * @return page of clinical notes
     */
    Page<ClinicalNote> findByPatientIdAndNoteTypeOrderByRecordedAtDesc(
            UUID patientId, NoteType noteType, Pageable pageable);
}
