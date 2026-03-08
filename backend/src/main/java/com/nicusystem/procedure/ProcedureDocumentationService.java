package com.nicusystem.procedure;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing procedure documentation records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProcedureDocumentationService {

    private final ProcedureDocumentationRepository procedureDocumentationRepository;
    private final ProcedureDocumentationMapper procedureDocumentationMapper;

    /**
     * Creates a new procedure documentation record.
     *
     * @param request the procedure documentation creation request
     * @return the created procedure documentation DTO
     */
    @Transactional
    public ProcedureDocumentationDto create(
            final CreateProcedureDocumentationRequest request) {
        final ProcedureDocumentation entity =
                procedureDocumentationMapper.toEntity(request);
        final ProcedureDocumentation saved =
                procedureDocumentationRepository.save(entity);
        log.info("Procedure documentation created: type={}, patientId={}",
                request.procedureType(), request.patientId());
        return procedureDocumentationMapper.toDto(saved);
    }

    /**
     * Retrieves a procedure documentation record by ID.
     *
     * @param id the procedure documentation UUID
     * @return the procedure documentation DTO
     */
    @Transactional(readOnly = true)
    public ProcedureDocumentationDto getById(final UUID id) {
        return procedureDocumentationRepository.findById(id)
                .map(procedureDocumentationMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProcedureDocumentation", id.toString()));
    }

    /**
     * Gets procedure documentation records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of procedure documentation DTOs
     */
    @Transactional(readOnly = true)
    public Page<ProcedureDocumentationDto> getByPatient(
            final UUID patientId, final Pageable pageable) {
        return procedureDocumentationRepository
                .findByPatientIdOrderByPerformedAtDesc(patientId, pageable)
                .map(procedureDocumentationMapper::toDto);
    }

    /**
     * Gets procedure documentation records for a patient filtered by type.
     *
     * @param patientId     the patient UUID
     * @param procedureType the procedure type
     * @param pageable      pagination information
     * @return page of procedure documentation DTOs
     */
    @Transactional(readOnly = true)
    public Page<ProcedureDocumentationDto> getByPatientAndType(
            final UUID patientId,
            final ProcedureType procedureType,
            final Pageable pageable) {
        return procedureDocumentationRepository
                .findByPatientIdAndProcedureTypeOrderByPerformedAtDesc(
                        patientId, procedureType, pageable)
                .map(procedureDocumentationMapper::toDto);
    }
}
