package com.nicusystem.fluid;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for FluidEntry entities.
 */
@Repository
public interface FluidEntryRepository extends JpaRepository<FluidEntry, UUID> {

    /**
     * Finds fluid entries for a patient ordered by recorded time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of fluid entries
     */
    Page<FluidEntry> findByPatientIdOrderByRecordedAtDesc(
            UUID patientId, Pageable pageable);

    /**
     * Finds fluid entries for a patient within a time range, ordered ascending.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range (inclusive)
     * @param end       end of the time range (inclusive)
     * @return list of fluid entries ordered by recorded time ascending
     */
    List<FluidEntry> findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
            UUID patientId, Instant start, Instant end);

    /**
     * Finds fluid entries for a patient filtered by entry type within a time range.
     *
     * @param patientId the patient UUID
     * @param entryType the fluid entry type
     * @param start     start of the time range (inclusive)
     * @param end       end of the time range (inclusive)
     * @return list of fluid entries
     */
    List<FluidEntry> findByPatientIdAndEntryTypeAndRecordedAtBetween(
            UUID patientId, FluidEntryType entryType, Instant start, Instant end);
}
