package com.nicusystem.nirs;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link NirsReading} entities.
 */
public interface NirsReadingRepository extends JpaRepository<NirsReading, UUID> {

    /**
     * Returns a page of NIRS readings for the given patient ordered by recorded time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of NIRS readings
     */
    Page<NirsReading> findByPatientIdOrderByRecordedAtDesc(UUID patientId, Pageable pageable);

    /**
     * Returns a page of NIRS readings for the given patient and site ordered by recorded time descending.
     *
     * @param patientId the patient UUID
     * @param site      the NIRS sensor site
     * @param pageable  pagination information
     * @return page of NIRS readings filtered by site
     */
    Page<NirsReading> findByPatientIdAndSiteOrderByRecordedAtDesc(
            UUID patientId, NirsSite site, Pageable pageable);

    /**
     * Returns NIRS readings for the given patient within a time range ordered by recorded time ascending.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range (inclusive)
     * @param end       the end of the time range (inclusive)
     * @return list of NIRS readings within the time range
     */
    List<NirsReading> findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
            UUID patientId, Instant start, Instant end);
}
