package com.nicusystem.vitals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for VitalSign entities.
 */
@Repository
public interface VitalSignRepository extends JpaRepository<VitalSign, UUID> {

    /**
     * Finds vital signs for a patient ordered by recorded time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of vital signs
     */
    Page<VitalSign> findByPatientIdOrderByRecordedAtDesc(UUID patientId, Pageable pageable);

    /**
     * Finds vital signs for a patient filtered by type.
     *
     * @param patientId the patient UUID
     * @param vitalType the vital sign type
     * @param pageable  pagination information
     * @return page of vital signs
     */
    Page<VitalSign> findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
            UUID patientId, VitalSignType vitalType, Pageable pageable);

    /**
     * Finds vital signs for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range
     * @param end       end of the time range
     * @return list of vital signs ordered by recorded time
     */
    List<VitalSign> findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
            UUID patientId, Instant start, Instant end);
}
