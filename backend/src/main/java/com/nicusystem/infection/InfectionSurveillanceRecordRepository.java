package com.nicusystem.infection;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for infection surveillance records.
 */
public interface InfectionSurveillanceRecordRepository
        extends JpaRepository<InfectionSurveillanceRecord, UUID> {

    /**
     * Finds all records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination info
     * @return page of records
     */
    Page<InfectionSurveillanceRecord> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds records by patient and surveillance type.
     *
     * @param patientId        the patient UUID
     * @param surveillanceType the type to filter by
     * @param pageable         pagination info
     * @return page of records
     */
    Page<InfectionSurveillanceRecord> findByPatientIdAndSurveillanceType(
            UUID patientId, InfectionSurveillanceType surveillanceType, Pageable pageable);
}
