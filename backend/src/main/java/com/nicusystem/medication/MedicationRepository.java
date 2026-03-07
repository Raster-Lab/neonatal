package com.nicusystem.medication;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Medication entities.
 */
@Repository
public interface MedicationRepository extends JpaRepository<Medication, UUID> {

    /**
     * Finds medications for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of medications
     */
    Page<Medication> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds medications for a patient filtered by status.
     *
     * @param patientId the patient UUID
     * @param status    the medication status
     * @param pageable  pagination information
     * @return page of medications
     */
    Page<Medication> findByPatientIdAndStatus(
            UUID patientId, MedicationStatus status, Pageable pageable);
}
