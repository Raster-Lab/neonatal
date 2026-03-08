package com.nicusystem.rounding;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for DailyRoundingSummary entities.
 */
@Repository
public interface DailyRoundingSummaryRepository
        extends JpaRepository<DailyRoundingSummary, UUID> {

    /**
     * Finds rounding summaries for a patient ordered by rounding date descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of daily rounding summary records
     */
    Page<DailyRoundingSummary> findByPatientIdOrderByRoundingDateDesc(
            UUID patientId, Pageable pageable);

    /**
     * Finds a rounding summary for a patient on a specific date.
     *
     * @param patientId    the patient UUID
     * @param roundingDate the rounding date
     * @return optional rounding summary
     */
    Optional<DailyRoundingSummary> findByPatientIdAndRoundingDate(
            UUID patientId, LocalDate roundingDate);
}
