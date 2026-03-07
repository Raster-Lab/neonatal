package com.nicusystem.respiratory;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link RespiratoryRecord} entities.
 */
public interface RespiratoryRecordRepository extends JpaRepository<RespiratoryRecord, UUID> {

    /**
     * Returns a page of respiratory records for the given patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of respiratory records
     */
    Page<RespiratoryRecord> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Returns a page of respiratory records for the given patient ordered by
     * recorded time descending (most recent first).
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of respiratory records ordered by recorded time descending
     */
    Page<RespiratoryRecord> findByPatientIdOrderByRecordedAtDesc(UUID patientId, Pageable pageable);
}
