package com.nicusystem.lab;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for LabResult entities.
 */
public interface LabResultRepository extends JpaRepository<LabResult, UUID> {

    /**
     * Finds all lab results for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of lab results
     */
    Page<LabResult> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds all lab results for a specific lab order.
     *
     * @param labOrderId the lab order UUID
     * @return list of lab results for the order
     */
    List<LabResult> findByLabOrderId(UUID labOrderId);

    /**
     * Finds lab results for a patient filtered by critical flag.
     *
     * @param patientId  the patient UUID
     * @param isCritical the critical flag filter
     * @param pageable   pagination information
     * @return page of lab results matching the critical flag
     */
    Page<LabResult> findByPatientIdAndIsCritical(UUID patientId, boolean isCritical, Pageable pageable);
}
