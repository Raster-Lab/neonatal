package com.nicusystem.flowsheet;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for FlowsheetEntry entities.
 */
@Repository
public interface FlowsheetEntryRepository
        extends JpaRepository<FlowsheetEntry, UUID> {

    /**
     * Finds flowsheet entries for a patient ordered by entry time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of flowsheet entry records
     */
    Page<FlowsheetEntry> findByPatientIdOrderByEntryTimeDesc(
            UUID patientId, Pageable pageable);

    /**
     * Finds flowsheet entries for a patient filtered by category,
     * ordered by entry time descending.
     *
     * @param patientId the patient UUID
     * @param category  the flowsheet category
     * @param pageable  pagination information
     * @return page of flowsheet entry records
     */
    Page<FlowsheetEntry> findByPatientIdAndCategoryOrderByEntryTimeDesc(
            UUID patientId, FlowsheetCategory category,
            Pageable pageable);

    /**
     * Finds flowsheet entries for a patient within a time range,
     * ordered by entry time ascending.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range (inclusive)
     * @param end       end of the time range (inclusive)
     * @return list of flowsheet entry records
     */
    List<FlowsheetEntry> findByPatientIdAndEntryTimeBetweenOrderByEntryTimeAsc(
            UUID patientId, Instant start, Instant end);
}
