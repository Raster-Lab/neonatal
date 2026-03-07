package com.nicusystem.transfer;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for PatientTransfer entities.
 */
public interface PatientTransferRepository extends JpaRepository<PatientTransfer, UUID> {

    /**
     * Finds all transfers for a given patient.
     *
     * @param patientId the patient UUID
     * @return list of transfers
     */
    List<PatientTransfer> findByPatientId(UUID patientId);

    /**
     * Counts the number of transfers for a given patient.
     *
     * @param patientId the patient UUID
     * @return count of transfers
     */
    long countByPatientId(UUID patientId);
}
