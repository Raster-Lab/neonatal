package com.nicusystem.infection;

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
 * Service for managing isolation precautions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IsolationPrecautionService {

    private final IsolationPrecautionRepository repository;
    private final IsolationPrecautionMapper mapper;

    /**
     * Creates a new isolation precaution.
     *
     * @param request the creation request
     * @return the created precaution DTO
     */
    @Transactional
    public IsolationPrecautionDto createPrecaution(
            final CreateIsolationPrecautionRequest request) {
        final IsolationPrecaution entity = mapper.toEntity(request);
        final IsolationPrecaution saved = repository.save(entity);
        log.info("Isolation precaution created: type={}, patientId={}",
                request.precautionType(), request.patientId());
        return mapper.toDto(saved);
    }

    /**
     * Retrieves an isolation precaution by ID.
     *
     * @param id the precaution UUID
     * @return the precaution DTO
     */
    @Transactional(readOnly = true)
    public IsolationPrecautionDto getPrecautionById(final UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "IsolationPrecaution", id.toString()));
    }

    /**
     * Gets all isolation precautions for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination info
     * @return page of precaution DTOs
     */
    @Transactional(readOnly = true)
    public Page<IsolationPrecautionDto> getPrecautionsByPatient(
            final UUID patientId, final Pageable pageable) {
        return repository.findByPatientId(patientId, pageable).map(mapper::toDto);
    }

    /**
     * Gets isolation precautions by patient and type.
     *
     * @param patientId      the patient UUID
     * @param precautionType the precaution type
     * @param pageable       pagination info
     * @return page of precaution DTOs
     */
    @Transactional(readOnly = true)
    public Page<IsolationPrecautionDto> getPrecautionsByPatientAndType(
            final UUID patientId,
            final IsolationPrecautionType precautionType,
            final Pageable pageable) {
        return repository.findByPatientIdAndPrecautionType(
                patientId, precautionType, pageable).map(mapper::toDto);
    }

    /**
     * Discontinues an isolation precaution by setting its end time.
     *
     * @param id the precaution UUID
     * @return the updated precaution DTO
     */
    @Transactional
    public IsolationPrecautionDto discontinuePrecaution(final UUID id) {
        final IsolationPrecaution precaution = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "IsolationPrecaution", id.toString()));
        precaution.setDiscontinuedAt(Instant.now());
        final IsolationPrecaution saved = repository.save(precaution);
        log.info("Isolation precaution discontinued: id={}", id);
        return mapper.toDto(saved);
    }
}
