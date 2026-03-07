package com.nicusystem.lab;

import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing laboratory results in the NICU.
 *
 * <p>Handles recording and retrieval of individual test results within lab panels,
 * including critical value identification and patient-level result queries.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LabResultService {

    private final LabResultRepository labResultRepository;
    private final LabResultMapper labResultMapper;

    /**
     * Records a new laboratory result.
     *
     * @param request the lab result creation request
     * @return the persisted lab result DTO
     */
    @Transactional
    public LabResultDto recordLabResult(final CreateLabResultRequest request) {
        final LabResult entity = labResultMapper.toEntity(request);
        final LabResult saved = labResultRepository.save(entity);
        log.info("Lab result recorded: testName={}, patientId={}, isCritical={}",
                request.testName(), request.patientId(), request.isCritical());
        return labResultMapper.toDto(saved);
    }

    /**
     * Retrieves a lab result by its unique identifier.
     *
     * @param id the lab result UUID
     * @return the lab result DTO
     * @throws ResourceNotFoundException if no lab result exists with the given ID
     */
    @Transactional(readOnly = true)
    public LabResultDto getLabResultById(final UUID id) {
        return labResultRepository.findById(id)
                .map(labResultMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("LabResult", id.toString()));
    }

    /**
     * Returns a paginated list of lab results for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of lab result DTOs
     */
    @Transactional(readOnly = true)
    public Page<LabResultDto> getLabResultsByPatient(final UUID patientId, final Pageable pageable) {
        return labResultRepository.findByPatientId(patientId, pageable)
                .map(labResultMapper::toDto);
    }

    /**
     * Returns all lab results for a specific lab order.
     *
     * @param labOrderId the lab order UUID
     * @return list of lab result DTOs for the order
     */
    @Transactional(readOnly = true)
    public List<LabResultDto> getLabResultsByLabOrder(final UUID labOrderId) {
        return labResultRepository.findByLabOrderId(labOrderId)
                .stream()
                .map(labResultMapper::toDto)
                .toList();
    }

    /**
     * Returns a paginated list of critical lab results for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of critical lab result DTOs
     */
    @Transactional(readOnly = true)
    public Page<LabResultDto> getCriticalLabResultsByPatient(
            final UUID patientId, final Pageable pageable) {
        return labResultRepository.findByPatientIdAndIsCritical(patientId, true, pageable)
                .map(labResultMapper::toDto);
    }
}
