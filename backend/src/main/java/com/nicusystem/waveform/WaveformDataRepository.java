package com.nicusystem.waveform;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for WaveformData entities.
 */
@Repository
public interface WaveformDataRepository extends JpaRepository<WaveformData, UUID> {

    /**
     * Finds waveform data for a patient ordered by start time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of waveform data
     */
    Page<WaveformData> findByPatientIdOrderByStartTimeDesc(
            UUID patientId, Pageable pageable);

    /**
     * Finds waveform data for a patient filtered by type, ordered by start time descending.
     *
     * @param patientId    the patient UUID
     * @param waveformType the waveform type
     * @param pageable     pagination information
     * @return page of waveform data
     */
    Page<WaveformData> findByPatientIdAndWaveformTypeOrderByStartTimeDesc(
            UUID patientId, WaveformType waveformType, Pageable pageable);

    /**
     * Finds waveform data for a patient within a time range, ordered by start time ascending.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range
     * @param end       the end of the time range
     * @return list of waveform data ordered by start time ascending
     */
    List<WaveformData> findByPatientIdAndStartTimeBetweenOrderByStartTimeAsc(
            UUID patientId, Instant start, Instant end);
}
