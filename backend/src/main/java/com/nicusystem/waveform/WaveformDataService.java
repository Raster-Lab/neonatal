package com.nicusystem.waveform;

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
 * Service for managing real-time waveform data.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WaveformDataService {

    private final WaveformDataRepository waveformDataRepository;
    private final WaveformDataMapper waveformDataMapper;

    /**
     * Creates a new waveform data record.
     *
     * @param request the waveform data creation request
     * @return the created waveform data DTO
     */
    @Transactional
    public WaveformDataDto create(final CreateWaveformDataRequest request) {
        final WaveformData entity = waveformDataMapper.toEntity(request);
        final WaveformData saved = waveformDataRepository.save(entity);
        log.info("Waveform data recorded: type={}, patientId={}",
                request.waveformType(), request.patientId());
        return waveformDataMapper.toDto(saved);
    }

    /**
     * Retrieves waveform data by ID.
     *
     * @param id the waveform data UUID
     * @return the waveform data DTO
     */
    @Transactional(readOnly = true)
    public WaveformDataDto getById(final UUID id) {
        return waveformDataRepository.findById(id)
                .map(waveformDataMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WaveformData", id.toString()));
    }

    /**
     * Gets waveform data for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of waveform data DTOs
     */
    @Transactional(readOnly = true)
    public Page<WaveformDataDto> getByPatient(
            final UUID patientId, final Pageable pageable) {
        return waveformDataRepository
                .findByPatientIdOrderByStartTimeDesc(patientId, pageable)
                .map(waveformDataMapper::toDto);
    }

    /**
     * Gets waveform data for a patient filtered by type.
     *
     * @param patientId    the patient UUID
     * @param waveformType the waveform type
     * @param pageable     pagination information
     * @return page of waveform data DTOs
     */
    @Transactional(readOnly = true)
    public Page<WaveformDataDto> getByPatientAndType(
            final UUID patientId,
            final WaveformType waveformType,
            final Pageable pageable) {
        return waveformDataRepository
                .findByPatientIdAndWaveformTypeOrderByStartTimeDesc(
                        patientId, waveformType, pageable)
                .map(waveformDataMapper::toDto);
    }

    /**
     * Gets waveform data for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range
     * @param end       the end of the time range
     * @return list of waveform data DTOs ordered by start time ascending
     */
    @Transactional(readOnly = true)
    public List<WaveformDataDto> getByPatientAndTimeRange(
            final UUID patientId, final Instant start, final Instant end) {
        return waveformDataRepository
                .findByPatientIdAndStartTimeBetweenOrderByStartTimeAsc(
                        patientId, start, end)
                .stream()
                .map(waveformDataMapper::toDto)
                .toList();
    }
}
