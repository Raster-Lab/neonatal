package com.nicusystem.medication;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing continuous infusions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContinuousInfusionService {

    private final ContinuousInfusionRepository continuousInfusionRepository;
    private final ContinuousInfusionMapper continuousInfusionMapper;

    /**
     * Creates a new continuous infusion order.
     *
     * @param request the creation request
     * @return the created continuous infusion DTO
     */
    @Transactional
    public ContinuousInfusionDto createInfusion(
            final CreateContinuousInfusionRequest request) {
        final ContinuousInfusion entity =
                continuousInfusionMapper.toEntity(request);
        entity.setStatus(InfusionStatus.ORDERED);
        final ContinuousInfusion saved =
                continuousInfusionRepository.save(entity);
        log.info("Continuous infusion created: patientId={}, drug={}",
                request.patientId(), request.drugName());
        return continuousInfusionMapper.toDto(saved);
    }

    /**
     * Retrieves a continuous infusion by ID.
     *
     * @param id the infusion UUID
     * @return the continuous infusion DTO
     */
    @Transactional(readOnly = true)
    public ContinuousInfusionDto getInfusionById(final UUID id) {
        return continuousInfusionRepository.findById(id)
                .map(continuousInfusionMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ContinuousInfusion", id.toString()));
    }

    /**
     * Gets continuous infusions for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of continuous infusion DTOs
     */
    @Transactional(readOnly = true)
    public Page<ContinuousInfusionDto> getInfusionsByPatient(
            final UUID patientId, final Pageable pageable) {
        return continuousInfusionRepository
                .findByPatientId(patientId, pageable)
                .map(continuousInfusionMapper::toDto);
    }

    /**
     * Gets continuous infusions by patient and status.
     *
     * @param patientId the patient UUID
     * @param status    the infusion status
     * @param pageable  pagination information
     * @return page of continuous infusion DTOs
     */
    @Transactional(readOnly = true)
    public Page<ContinuousInfusionDto> getInfusionsByPatientAndStatus(
            final UUID patientId,
            final InfusionStatus status,
            final Pageable pageable) {
        return continuousInfusionRepository
                .findByPatientIdAndStatus(patientId, status, pageable)
                .map(continuousInfusionMapper::toDto);
    }

    /**
     * Updates the status of a continuous infusion.
     *
     * @param id     the infusion UUID
     * @param status the new status
     * @return the updated continuous infusion DTO
     */
    @Transactional
    public ContinuousInfusionDto updateInfusionStatus(
            final UUID id, final InfusionStatus status) {
        final ContinuousInfusion infusion =
                continuousInfusionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "ContinuousInfusion", id.toString()));
        infusion.setStatus(status);
        final ContinuousInfusion saved =
                continuousInfusionRepository.save(infusion);
        log.info("Continuous infusion status updated: id={}, status={}",
                id, status);
        return continuousInfusionMapper.toDto(saved);
    }

    /**
     * Adjusts the rate of a continuous infusion.
     *
     * @param id      the infusion UUID
     * @param newRate the new rate
     * @return the updated continuous infusion DTO
     */
    @Transactional
    public ContinuousInfusionDto adjustRate(
            final UUID id, final Double newRate) {
        final ContinuousInfusion infusion =
                continuousInfusionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "ContinuousInfusion", id.toString()));
        infusion.setRate(newRate);
        infusion.setStatus(InfusionStatus.TITRATING);
        final ContinuousInfusion saved =
                continuousInfusionRepository.save(infusion);
        log.info("Continuous infusion rate adjusted: id={}, newRate={}",
                id, newRate);
        return continuousInfusionMapper.toDto(saved);
    }
}
