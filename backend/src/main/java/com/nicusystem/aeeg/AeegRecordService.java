package com.nicusystem.aeeg;

import java.time.Instant;
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
 * Service for managing amplitude-integrated EEG (aEEG) records in the NICU.
 *
 * <p>Handles creation and retrieval of aEEG monitoring records,
 * including seizure detection queries and time-range filtering.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AeegRecordService {

    private final AeegRecordRepository aeegRecordRepository;
    private final AeegRecordMapper aeegRecordMapper;

    /**
     * Creates a new aEEG record for a patient.
     *
     * @param request the aEEG record creation request
     * @return the persisted aEEG record DTO
     */
    @Transactional
    public AeegRecordDto create(final CreateAeegRecordRequest request) {
        final AeegRecord entity = aeegRecordMapper.toEntity(request);
        final AeegRecord saved = aeegRecordRepository.save(entity);
        log.info("aEEG record created: classification={}, patientId={}",
                request.classification(), request.patientId());
        return aeegRecordMapper.toDto(saved);
    }

    /**
     * Retrieves an aEEG record by its unique identifier.
     *
     * @param id the aEEG record UUID
     * @return the aEEG record DTO
     * @throws ResourceNotFoundException if no record exists with the given ID
     */
    @Transactional(readOnly = true)
    public AeegRecordDto getById(final UUID id) {
        return aeegRecordRepository.findById(id)
                .map(aeegRecordMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("AeegRecord", id.toString()));
    }

    /**
     * Returns a paginated list of aEEG records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of aEEG record DTOs
     */
    @Transactional(readOnly = true)
    public Page<AeegRecordDto> getByPatient(final UUID patientId, final Pageable pageable) {
        return aeegRecordRepository
                .findByPatientIdOrderByRecordingStartTimeDesc(patientId, pageable)
                .map(aeegRecordMapper::toDto);
    }

    /**
     * Returns aEEG records where seizure activity was detected for a patient.
     *
     * @param patientId the patient UUID
     * @return list of aEEG record DTOs with seizure detected
     */
    @Transactional(readOnly = true)
    public List<AeegRecordDto> getSeizuresByPatient(final UUID patientId) {
        return aeegRecordRepository
                .findByPatientIdAndSeizureDetectedTrueOrderByRecordingStartTimeDesc(patientId)
                .stream()
                .map(aeegRecordMapper::toDto)
                .toList();
    }

    /**
     * Returns aEEG records for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range
     * @param end       the end of the time range
     * @return list of aEEG record DTOs within the time range
     */
    @Transactional(readOnly = true)
    public List<AeegRecordDto> getByPatientAndTimeRange(
            final UUID patientId, final Instant start, final Instant end) {
        return aeegRecordRepository
                .findByPatientIdAndRecordingStartTimeBetweenOrderByRecordingStartTimeAsc(
                        patientId, start, end)
                .stream()
                .map(aeegRecordMapper::toDto)
                .toList();
    }
}
