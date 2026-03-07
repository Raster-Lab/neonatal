package com.nicusystem.infection;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for isolation precaution records.
 */
public interface IsolationPrecautionRepository
        extends JpaRepository<IsolationPrecaution, UUID> {

    /**
     * Finds all precautions for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination info
     * @return page of precautions
     */
    Page<IsolationPrecaution> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds precautions by patient and type.
     *
     * @param patientId      the patient UUID
     * @param precautionType the precaution type
     * @param pageable       pagination info
     * @return page of precautions
     */
    Page<IsolationPrecaution> findByPatientIdAndPrecautionType(
            UUID patientId, IsolationPrecautionType precautionType, Pageable pageable);
}
