package com.nicusystem.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for continuous infusion persistence operations.
 */
@Repository
public interface ContinuousInfusionRepository extends JpaRepository<ContinuousInfusion, UUID> {

    /**
     * Finds continuous infusions by patient ID.
     *
     * @param patientId the patient identifier
     * @param pageable pagination information
     * @return a page of continuous infusions
     */
    Page<ContinuousInfusion> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds continuous infusions by patient ID and status.
     *
     * @param patientId the patient identifier
     * @param status the infusion status
     * @param pageable pagination information
     * @return a page of continuous infusions
     */
    Page<ContinuousInfusion> findByPatientIdAndStatus(UUID patientId, InfusionStatus status, Pageable pageable);

    /**
     * Finds continuous infusions by patient ID and drug name.
     *
     * @param patientId the patient identifier
     * @param drugName the drug name
     * @return a list of continuous infusions
     */
    List<ContinuousInfusion> findByPatientIdAndDrugName(UUID patientId, String drugName);
}
