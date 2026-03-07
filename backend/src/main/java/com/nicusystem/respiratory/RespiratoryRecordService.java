package com.nicusystem.respiratory;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing respiratory support records in the NICU.
 *
 * <p>Handles creation and retrieval of ventilator and respiratory support parameters,
 * as well as oxygenation index calculations.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RespiratoryRecordService {

    private final RespiratoryRecordRepository respiratoryRecordRepository;
    private final RespiratoryRecordMapper respiratoryRecordMapper;

    /**
     * Creates a new respiratory support record for a patient.
     *
     * @param request the respiratory record creation request
     * @return the persisted respiratory record DTO
     */
    @Transactional
    public RespiratoryRecordDto createRecord(final CreateRespiratoryRecordRequest request) {
        final RespiratoryRecord entity = respiratoryRecordMapper.toEntity(request);
        final RespiratoryRecord saved = respiratoryRecordRepository.save(entity);
        log.info("Respiratory record created: supportMode={}, patientId={}",
                request.supportMode(), request.patientId());
        return respiratoryRecordMapper.toDto(saved);
    }

    /**
     * Retrieves a respiratory record by its unique identifier.
     *
     * @param id the respiratory record UUID
     * @return the respiratory record DTO
     * @throws ResourceNotFoundException if no record exists with the given ID
     */
    @Transactional(readOnly = true)
    public RespiratoryRecordDto getRecordById(final UUID id) {
        return respiratoryRecordRepository.findById(id)
                .map(respiratoryRecordMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("RespiratoryRecord", id.toString()));
    }

    /**
     * Returns a paginated list of respiratory records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of respiratory record DTOs
     */
    @Transactional(readOnly = true)
    public Page<RespiratoryRecordDto> getRecordsByPatient(
            final UUID patientId, final Pageable pageable) {
        return respiratoryRecordRepository.findByPatientId(patientId, pageable)
                .map(respiratoryRecordMapper::toDto);
    }

    /**
     * Returns a paginated list of respiratory records for a patient, ordered
     * by recorded time descending (most recent first).
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of respiratory record DTOs ordered by recorded time descending
     */
    @Transactional(readOnly = true)
    public Page<RespiratoryRecordDto> getLatestRecordsByPatient(
            final UUID patientId, final Pageable pageable) {
        return respiratoryRecordRepository
                .findByPatientIdOrderByRecordedAtDesc(patientId, pageable)
                .map(respiratoryRecordMapper::toDto);
    }

    /**
     * Calculates the Oxygenation Index (OI) from ventilator and blood gas parameters.
     *
     * <p>Formula: OI = (FiO2 / 100 × MAP × 100) / PaO2</p>
     *
     * @param fio2Percent fraction of inspired oxygen percentage (21–100)
     * @param mapCmh2o    mean airway pressure in cmH2O
     * @param pao2Mmhg    arterial partial pressure of oxygen in mmHg (from ABG)
     * @return the calculated oxygenation index
     */
    public Double calculateOxygenationIndex(
            final Double fio2Percent,
            final Double mapCmh2o,
            final Double pao2Mmhg) {
        return (fio2Percent / 100.0 * mapCmh2o * 100.0) / pao2Mmhg;
    }
}
