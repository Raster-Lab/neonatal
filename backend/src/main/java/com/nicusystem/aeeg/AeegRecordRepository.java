package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link AeegRecord} entities.
 */
public interface AeegRecordRepository extends JpaRepository<AeegRecord, UUID> {

    /**
     * Returns a page of aEEG records for the given patient ordered by recording start time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of aEEG records
     */
    Page<AeegRecord> findByPatientIdOrderByRecordingStartTimeDesc(UUID patientId, Pageable pageable);

    /**
     * Returns aEEG records where seizure was detected for the given patient.
     *
     * @param patientId the patient UUID
     * @return list of aEEG records with seizure detected
     */
    List<AeegRecord> findByPatientIdAndSeizureDetectedTrueOrderByRecordingStartTimeDesc(UUID patientId);

    /**
     * Returns aEEG records for the given patient within a time range ordered by start time ascending.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range (inclusive)
     * @param end       the end of the time range (inclusive)
     * @return list of aEEG records within the time range
     */
    List<AeegRecord> findByPatientIdAndRecordingStartTimeBetweenOrderByRecordingStartTimeAsc(
            UUID patientId, Instant start, Instant end);
}
