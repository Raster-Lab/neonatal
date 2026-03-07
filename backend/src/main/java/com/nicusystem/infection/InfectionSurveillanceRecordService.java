package com.nicusystem.infection;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing infection surveillance records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InfectionSurveillanceRecordService {

    private final InfectionSurveillanceRecordRepository repository;
    private final InfectionSurveillanceRecordMapper mapper;

    /**
     * Creates a new infection surveillance record.
     *
     * @param request the creation request
     * @return the created record DTO
     */
    @Transactional
    public InfectionSurveillanceRecordDto createRecord(
            final CreateInfectionSurveillanceRecordRequest request) {
        final InfectionSurveillanceRecord entity = mapper.toEntity(request);
        final InfectionSurveillanceRecord saved = repository.save(entity);
        log.info("Infection surveillance record created: type={}, patientId={}",
                request.surveillanceType(), request.patientId());
        return mapper.toDto(saved);
    }

    /**
     * Retrieves an infection surveillance record by ID.
     *
     * @param id the record UUID
     * @return the record DTO
     */
    @Transactional(readOnly = true)
    public InfectionSurveillanceRecordDto getRecordById(final UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "InfectionSurveillanceRecord", id.toString()));
    }

    /**
     * Gets all infection surveillance records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination info
     * @return page of record DTOs
     */
    @Transactional(readOnly = true)
    public Page<InfectionSurveillanceRecordDto> getRecordsByPatient(
            final UUID patientId, final Pageable pageable) {
        return repository.findByPatientId(patientId, pageable).map(mapper::toDto);
    }

    /**
     * Gets infection surveillance records by patient and surveillance type.
     *
     * @param patientId        the patient UUID
     * @param surveillanceType the type to filter by
     * @param pageable         pagination info
     * @return page of record DTOs
     */
    @Transactional(readOnly = true)
    public Page<InfectionSurveillanceRecordDto> getRecordsByPatientAndType(
            final UUID patientId,
            final InfectionSurveillanceType surveillanceType,
            final Pageable pageable) {
        return repository.findByPatientIdAndSurveillanceType(
                patientId, surveillanceType, pageable).map(mapper::toDto);
    }
}
