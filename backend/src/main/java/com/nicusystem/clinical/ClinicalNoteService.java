package com.nicusystem.clinical;

import java.time.Instant;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing clinical notes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicalNoteService {

    private final ClinicalNoteRepository clinicalNoteRepository;
    private final ClinicalNoteMapper clinicalNoteMapper;

    /**
     * Creates a new clinical note.
     *
     * @param request the clinical note creation request
     * @return the created clinical note DTO
     */
    @Transactional
    public ClinicalNoteDto createNote(final CreateClinicalNoteRequest request) {
        final ClinicalNote note = clinicalNoteMapper.toEntity(request);
        final ClinicalNote saved = clinicalNoteRepository.save(note);
        log.info("Clinical note created: type={}, patientId={}, authorId={}",
                request.noteType(), request.patientId(), request.authorId());
        return clinicalNoteMapper.toDto(saved);
    }

    /**
     * Retrieves a clinical note by ID.
     *
     * @param id the clinical note UUID
     * @return the clinical note DTO
     */
    @Transactional(readOnly = true)
    public ClinicalNoteDto getNoteById(final UUID id) {
        return clinicalNoteRepository.findById(id)
                .map(clinicalNoteMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ClinicalNote", id.toString()));
    }

    /**
     * Gets clinical notes for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of clinical note DTOs
     */
    @Transactional(readOnly = true)
    public Page<ClinicalNoteDto> getNotesByPatient(
            final UUID patientId, final Pageable pageable) {
        return clinicalNoteRepository
                .findByPatientIdOrderByRecordedAtDesc(patientId, pageable)
                .map(clinicalNoteMapper::toDto);
    }

    /**
     * Gets clinical notes for a patient filtered by note type.
     *
     * @param patientId the patient UUID
     * @param noteType  the note type
     * @param pageable  pagination information
     * @return page of clinical note DTOs
     */
    @Transactional(readOnly = true)
    public Page<ClinicalNoteDto> getNotesByPatientAndType(
            final UUID patientId,
            final NoteType noteType,
            final Pageable pageable) {
        return clinicalNoteRepository
                .findByPatientIdAndNoteTypeOrderByRecordedAtDesc(
                        patientId, noteType, pageable)
                .map(clinicalNoteMapper::toDto);
    }

    /**
     * Adds a co-signature to an existing clinical note.
     *
     * @param id         the clinical note UUID
     * @param coSignerId the co-signer's identifier
     * @return the updated clinical note DTO
     */
    @Transactional
    public ClinicalNoteDto addCoSignature(final UUID id, final String coSignerId) {
        final ClinicalNote note = clinicalNoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ClinicalNote", id.toString()));
        note.setCoSignerId(coSignerId);
        note.setCoSignedAt(Instant.now());
        final ClinicalNote saved = clinicalNoteRepository.save(note);
        log.info("Co-signature added to clinical note: id={}, coSignerId={}", id, coSignerId);
        return clinicalNoteMapper.toDto(saved);
    }
}
