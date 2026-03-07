package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link ApneaEvent} entities.
 */
public interface ApneaEventRepository extends JpaRepository<ApneaEvent, UUID> {

    /**
     * Returns a page of apnea events for the given patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of apnea events
     */
    Page<ApneaEvent> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Returns a page of apnea events for the given patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range (inclusive)
     * @param end       end of the time range (inclusive)
     * @param pageable  pagination information
     * @return page of apnea events within the specified range
     */
    Page<ApneaEvent> findByPatientIdAndOccurredAtBetween(
            UUID patientId, Instant start, Instant end, Pageable pageable);

    /**
     * Counts total apnea events for the given patient.
     *
     * @param patientId the patient UUID
     * @return total count of apnea events
     */
    long countByPatientId(UUID patientId);
}
